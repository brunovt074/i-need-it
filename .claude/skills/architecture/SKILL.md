---
skill: architecture
description: Clean Architecture patterns for i-need-it Android app
scope: all layers
auto_invoke:
  - Creating domain entities or value objects
  - Creating or modifying repository interfaces
  - Creating use cases
  - Adding a new domain model
  - Refactoring package structure
---

# Architecture Skill — Clean Architecture / Android

## Identity

You are an architecture specialist for an Android app following Clean Architecture. Your job is to enforce the dependency rule, keep the domain layer pure, and ensure each layer has a single, clear responsibility.

## Dependency Rule

```
domain ← data ← di
  ↑
  ui (reads domain models directly)
  domain NEVER imports data, di, or ui
```

## Layer Responsibilities

| Layer | Package | Responsibility |
|-------|---------|----------------|
| Domain | `domain/model/` | Entities, value objects, enums |
| Domain | `domain/usecase/` | Business logic orchestration |
| Domain | `domain/repository/` | Repository interfaces (ports) — NOT here in this project; interfaces live in the usecase/domain package |
| Data | `data/local/` | Room entities, DAO, AppDatabase |
| Data | `data/repo/` | Repository implementations |
| Data | `data/prefs/` | DataStore preference wrappers |
| DI | `di/` | Hilt modules wiring everything |
| UI | `ui/` | Compose screens, ViewModels, theme |

## Critical Rules

### ALWAYS
- Domain entities are pure Kotlin — no Android, Hilt, Room annotations
- Repository interfaces defined alongside use cases in domain layer (no separate `domain/repository/` folder needed for this small app — keep interface in same file or at `domain/` root)
- Use cases are single-responsibility — one public method: `execute()` or a Flow-returning `observe()`
- Constructor injection everywhere
- `data class` for entities and value objects
- `sealed interface` or `enum class` for fixed sets

### NEVER
- Domain imports Room, Hilt, DataStore, Android SDK
- Use cases call other use cases (orchestrate at ViewModel level instead)
- Domain entities carry Android context or lifecycle references
- Business logic in composables

## Domain Model Patterns

### Entry (main entity)

```kotlin
package com.brunovt.ineedit.domain.model

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

data class Entry(
    val id: String,
    val column: Column,
    val name: String,
    val timeKey: TimeKey?,
    val specificDate: LocalDate?,
    val cost: Money?,
    val place: String?,
    val tags: List<TagKey>,
    val completedAt: Instant?,
    val createdAt: Instant,
    val updatedAt: Instant,
)
```

### Value Object Pattern

```kotlin
@JvmInline
value class Money private constructor(val raw: Long) {
    val amountMinor: Long get() = raw and 0xFFFFFFFF
    val currencyCode: String get() = TODO("encode in upper bits or use data class")
}

// Simpler — just use a plain data class for clarity:
data class Money(
    val amountMinor: Long,
    val currency: String,
)
```

### Enum Pattern

```kotlin
enum class Column { NEED, WANT, WISH }

enum class TimeKey {
    TODAY, THIS_WEEK, TWO_WEEKS, THIS_MONTH,
    THREE_MONTHS, SIX_MONTHS, ONE_YEAR, MORE_THAN_YEAR,
    SPECIFIC_DATE,
}

enum class TagKey {
    HEALTH, HOME, HOBBY, WORK, FAMILY,
    TRAVEL, MONEY, STUDY, FITNESS,
}
```

## Repository Interface Pattern

```kotlin
interface EntryRepository {
    fun observeActive(column: Column): Flow<List<Entry>>
    fun observeDone(): Flow<List<Entry>>
    suspend fun byId(id: String): Entry?
    suspend fun upsert(entry: Entry)
    suspend fun delete(id: String)
}
```

Conventions:
- `observe*` methods return `Flow<>` (never suspend)
- Mutation methods are `suspend`
- Single result lookups return nullable

## Use Case Pattern

```kotlin
class ObserveBoardUseCase(private val repository: EntryRepository) {
    operator fun invoke(column: Column): Flow<List<Entry>> =
        repository.observeActive(column)
}

class UpsertEntryUseCase(private val repository: EntryRepository) {
    suspend operator fun invoke(entry: Entry) {
        require(entry.name.isNotBlank()) { "Entry name must not be blank" }
        require(entry.timeKey == TimeKey.SPECIFIC_DATE == (entry.specificDate != null)) {
            "specificDate must be set if and only if timeKey == SPECIFIC_DATE"
        }
        repository.upsert(entry)
    }
}
```

Conventions:
- `operator fun invoke()` as the single public method
- `require()` for precondition validation (throws `IllegalArgumentException`)
- No try/catch — propagate to ViewModel
- Flow-based use cases are NOT suspend

## Invariants to Enforce

| Invariant | Where enforced |
|-----------|---------------|
| `name.isNotBlank()` | `UpsertEntryUseCase` |
| `timeKey == SPECIFIC_DATE ⟺ specificDate != null` | `UpsertEntryUseCase` |
| `cost.amountMinor != null ⟺ cost.currency != null` | `Money` constructor |
| `completedAt != null` = in Done history | `MarkDoneUseCase` |
| Moving an entry = updating `column` field only | `MoveColumnUseCase` |
