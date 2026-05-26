---
skill: room-db
description: Room database patterns for i-need-it — entities, DAOs, TypeConverters, AppDatabase
scope: data/local
auto_invoke:
  - Creating Room entity
  - Creating DAO
  - Writing TypeConverters
  - Modifying AppDatabase
  - Adding database migrations
---

# Room DB Skill — i-need-it

## Identity

You are a Room database specialist for an Android app. Your job is to produce correct, efficient Room entities, DAOs, TypeConverters, and migrations that map to the domain model without leaking ORM details into the domain layer.

## Critical Rules

### ALWAYS
- Keep `@Entity` classes in `data/local/` — never in `domain/`
- Map `EntryEntity` ↔ `Entry` (domain) in the repository, not in the DAO
- Use `@TypeConverter` for complex types (enums, lists, kotlinx-datetime types)
- Prefer `@Upsert` over manual insert+update logic
- Use `Flow<>` return types for observation queries — Room handles re-emission on writes
- Test with in-memory Room DB (`Room.inMemoryDatabaseBuilder`)

### NEVER
- Expose `EntryEntity` outside of `data/local/` and `data/repo/`
- Use raw SQL strings when Room's query DSL covers the case
- Forget `@TypeConverters(Converters::class)` on `AppDatabase`
- Share a single `AppDatabase` instance without `@Singleton`

## Entity Pattern

```kotlin
package com.brunovt.ineedit.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entries")
data class EntryEntity(
    @PrimaryKey val id: String,
    val column: String,                  // Column enum name
    val name: String,
    val timeKey: String?,                // TimeKey enum name or null
    val specificDate: String?,           // ISO-8601 LocalDate string or null
    val costAmountMinor: Long?,
    val costCurrency: String?,
    val place: String?,
    val tags: String,                    // JSON array of TagKey names
    val completedAt: Long?,              // Instant.epochSeconds or null
    val createdAt: Long,                 // Instant.epochSeconds
    val updatedAt: Long,                 // Instant.epochSeconds
)
```

Conventions:
- Store enums as their `.name` string — robust to serialization changes
- Store `kotlinx.datetime.Instant` as Long (epoch seconds)
- Store `LocalDate` as ISO-8601 string (e.g., `"2025-06-15"`)
- Store `List<TagKey>` as JSON string via TypeConverter

## DAO Pattern

```kotlin
package com.brunovt.ineedit.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {

    @Query("SELECT * FROM entries WHERE completedAt IS NULL AND `column` = :col ORDER BY updatedAt DESC, createdAt DESC")
    fun observeActive(col: String): Flow<List<EntryEntity>>

    @Query("SELECT * FROM entries WHERE completedAt IS NOT NULL ORDER BY completedAt DESC")
    fun observeDone(): Flow<List<EntryEntity>>

    @Query("SELECT * FROM entries WHERE id = :id")
    suspend fun byId(id: String): EntryEntity?

    @Upsert
    suspend fun upsert(e: EntryEntity)

    @Query("DELETE FROM entries WHERE id = :id")
    suspend fun delete(id: String)
}
```

Conventions:
- `column` is a SQL reserved word — escape with backticks in queries: `` `column` ``
- `Flow<>` returns for observation, `suspend` for mutations
- `@Upsert` handles insert-or-update atomically

## TypeConverters

```kotlin
package com.brunovt.ineedit.data.local

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class Converters {

    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromTagList(value: List<String>): String = json.encodeToString(value)

    @TypeConverter
    fun toTagList(value: String): List<String> = json.decodeFromString(value)
}
```

Note: Store tag names (strings), convert to `TagKey` enum in the mapper, not here.

## AppDatabase

```kotlin
package com.brunovt.ineedit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [EntryEntity::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun entryDao(): EntryDao
}
```

## Hilt Module

```kotlin
package com.brunovt.ineedit.di

import android.content.Context
import androidx.room.Room
import com.brunovt.ineedit.data.local.AppDatabase
import com.brunovt.ineedit.data.local.EntryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "ineedit.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideEntryDao(db: AppDatabase): EntryDao = db.entryDao()
}
```

## Domain ↔ Entity Mapper

```kotlin
package com.brunovt.ineedit.data.repo

import com.brunovt.ineedit.data.local.EntryEntity
import com.brunovt.ineedit.domain.model.*
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

fun EntryEntity.toDomain(): Entry = Entry(
    id = id,
    column = Column.valueOf(column),
    name = name,
    timeKey = timeKey?.let { TimeKey.valueOf(it) },
    specificDate = specificDate?.let { LocalDate.parse(it) },
    cost = if (costAmountMinor != null && costCurrency != null)
        Money(costAmountMinor, costCurrency) else null,
    place = place,
    tags = tags.split(",").filter { it.isNotBlank() }.map { TagKey.valueOf(it) },
    completedAt = completedAt?.let { Instant.fromEpochSeconds(it) },
    createdAt = Instant.fromEpochSeconds(createdAt),
    updatedAt = Instant.fromEpochSeconds(updatedAt),
)

fun Entry.toEntity(): EntryEntity = EntryEntity(
    id = id,
    column = column.name,
    name = name,
    timeKey = timeKey?.name,
    specificDate = specificDate?.toString(),
    costAmountMinor = cost?.amountMinor,
    costCurrency = cost?.currency,
    place = place,
    tags = tags.joinToString(",") { it.name },
    completedAt = completedAt?.epochSeconds,
    createdAt = createdAt.epochSeconds,
    updatedAt = updatedAt.epochSeconds,
)
```

## Repository Implementation

```kotlin
package com.brunovt.ineedit.data.repo

import com.brunovt.ineedit.data.local.EntryDao
import com.brunovt.ineedit.domain.model.Column
import com.brunovt.ineedit.domain.model.Entry
import com.brunovt.ineedit.domain.usecase.EntryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomEntryRepository @Inject constructor(
    private val dao: EntryDao
) : EntryRepository {

    override fun observeActive(column: Column): Flow<List<Entry>> =
        dao.observeActive(column.name).map { list -> list.map { it.toDomain() } }

    override fun observeDone(): Flow<List<Entry>> =
        dao.observeDone().map { list -> list.map { it.toDomain() } }

    override suspend fun byId(id: String): Entry? =
        dao.byId(id)?.toDomain()

    override suspend fun upsert(entry: Entry) =
        dao.upsert(entry.toEntity())

    override suspend fun delete(id: String) =
        dao.delete(id)
}
```

## Migration Strategy

- Schema exported to `app/schemas/` (set `room.schemaLocation` in build.gradle.kts)
- For v1 → v2: write explicit `Migration(1, 2)` objects
- Never use `fallbackToDestructiveMigration()` in production (debug only)
- Test migrations with `MigrationTestHelper`

## Integration Test Pattern

```kotlin
@RunWith(AndroidJUnit4::class)
class EntryDaoTest {

    @get:Rule
    val db = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        AppDatabase::class.java
    ).allowMainThreadQueries().build()

    private lateinit var dao: EntryDao

    @Before
    fun setUp() { dao = db.entryDao() }

    @After
    fun tearDown() { db.close() }

    @Test
    fun insertAndObserveActive() = runTest {
        val entity = EntryEntity(
            id = "uuid-1", column = "NEED", name = "Toothpaste",
            timeKey = "TODAY", specificDate = null,
            costAmountMinor = null, costCurrency = null,
            place = null, tags = "",
            completedAt = null,
            createdAt = 1000L, updatedAt = 1000L
        )
        dao.upsert(entity)

        val results = dao.observeActive("NEED").first()
        assertEquals(1, results.size)
        assertEquals("Toothpaste", results[0].name)
    }
}
```
