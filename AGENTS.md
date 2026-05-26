# i-need-it Android App — Agent Guidelines

> **Single Source of Truth** for all AI coding agents working on this project.
> Compatible with: Claude Code, OpenCode, Codex, Cursor, Windsurf, Gemini CLI, Copilot, Aider.

---

## Agent Startup Protocol

**Before writing any code**, every agent MUST:

1. Read this file completely
2. Read `PROJECT_STATUS.md` to understand current state
3. Identify the task type:
   - If it matches an **SDD workflow step** → Launch the corresponding subagent via `task` tool (skip step 4)
   - If it matches a **skill-based concern** → Read the relevant `.claude/skills/{name}/SKILL.md` file(s), then proceed to step 4
4. **For new dependencies/libraries** → Use **Context7** to research the library before adding it
5. Confirm the target branch before any file modification

> Skipping this protocol produces inconsistent code that will be rejected at review.

---

## Project Overview

| Field | Value |
|-------|-------|
| Name | i-need-it |
| Language | Kotlin (latest stable) |
| Platform | Android (minSdk 26, targetSdk latest stable) |
| Architecture | Clean Architecture / DDD |
| Database | Room (SQLite) |
| UI | Jetpack Compose + Material 3 |
| DI | Hilt |
| Preferences | DataStore (Preferences) |
| Async / State | Coroutines + Flow + StateFlow |
| Navigation | androidx.navigation:navigation-compose |
| Money | Internal `Money` value class (amountMinor: Long + ISO currency) |
| Date/Time | kotlinx-datetime |
| Remote (phase 2) | Supabase Kotlin client (`supabase-kt`) |
| Build | Gradle Kotlin DSL + version catalogs (`libs.versions.toml`) |

Package: `com.brunovt.ineedit`

---

## Setup & Validation Commands

```bash
# Build debug APK
./gradlew assembleDebug

# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest

# Run ktlint check
./gradlew ktlintCheck

# Run detekt
./gradlew detekt

# Full pre-merge validation (MUST pass — zero errors, zero failures)
./gradlew test ktlintCheck detekt assembleDebug
```

---

## Code Style

- **No comments** — code is self-documenting via good naming
- **No raw SQL** — Room DAO DSL exclusively
- **Single file per class** — no boilerplate files
- **`Money` value class** for all monetary values (amountMinor: Long + ISO currency)
- **kotlinx-datetime** for all date/time values
- **Clean Architecture** — domain NEVER imports data or presentation layers
- **UI text from `strings.xml`** only — `stringResource()` everywhere, never hardcoded
- **Code and tests in English**, UI strings in `strings.xml` (es + en)
- No wildcard imports
- ktlint + detekt enforced — zero warnings on release

---

## Architecture Overview

### Layer Layout

```
app/src/main/java/com/brunovt/ineedit/
├── MainActivity.kt                  ← single-activity, Compose-only
├── App.kt                           ← Hilt @HiltAndroidApp
├── di/
│   ├── DataStoreModule.kt
│   ├── DatabaseModule.kt
│   └── SyncModule.kt
├── data/
│   ├── local/
│   │   ├── EntryDao.kt
│   │   ├── EntryEntity.kt
│   │   ├── AppDatabase.kt
│   │   └── Converters.kt
│   ├── prefs/
│   │   ├── ThemePrefs.kt            ← ThemeFamily + ThemeMode flows
│   │   ├── LayoutPrefs.kt           ← accordion | fan
│   │   └── LocalePrefs.kt
│   ├── remote/                      ← phase 2
│   │   ├── SupabaseClient.kt
│   │   └── EntryRemote.kt
│   └── repo/
│       ├── EntryRepository.kt       ← single source of truth
│       └── SyncRepository.kt        ← phase 2, no-op stub in v1
├── domain/
│   ├── model/
│   │   ├── Entry.kt
│   │   ├── Column.kt
│   │   ├── TimeKey.kt
│   │   ├── Money.kt
│   │   └── TagKey.kt
│   └── usecase/
│       ├── ObserveBoardUseCase.kt
│       ├── UpsertEntryUseCase.kt
│       ├── MarkDoneUseCase.kt
│       └── MoveColumnUseCase.kt
└── ui/
    ├── theme/
    │   ├── AppTheme.kt              ← composable wrapper
    │   ├── AppTokens.kt             ← custom tokens + LocalAppTokens
    │   ├── PaperTheme.kt            ← Paper × light/dark values
    │   ├── MinimalTheme.kt          ← Minimal × light/dark values
    │   └── Motion.kt                ← easing curves + durations
    ├── components/
    │   ├── TopBar.kt
    │   ├── BottomNav.kt
    │   ├── ItemCard.kt
    │   ├── AddRow.kt
    │   ├── ColumnSurface.kt
    │   ├── Chip.kt
    │   ├── LayoutToggle.kt
    │   ├── LocaleSwitch.kt
    │   └── ThemeCardPicker.kt
    ├── dashboard/
    │   ├── DashboardScreen.kt
    │   ├── AccordionLayout.kt
    │   └── FanLayout.kt
    ├── modal/
    │   ├── EntryFormSheet.kt        ← bottom sheet on mobile
    │   ├── EntryFormDialog.kt       ← centered on tablet
    │   └── ItemForm.kt              ← shared body
    ├── history/
    │   └── DoneScreen.kt
    └── settings/
        └── SettingsScreen.kt
```

### Dependency Rule

```
domain ← data ← di
  ↑
  ui (presentation) depends on domain and data
  domain NEVER imports data, di, or ui
```

---

## Skills

Skills provide specialized context for each concern area. They are **not optional** — they encode decisions made through trial and error on this project.

### Available Skills

| Skill | File | Scope |
|-------|------|-------|
| `architecture` | `.claude/skills/architecture/SKILL.md` | All layers |
| `room-db` | `.claude/skills/room-db/SKILL.md` | data/local |
| `compose-android` | `.claude/skills/compose-android/SKILL.md` | ui |
| `theming` | `.claude/skills/theming/SKILL.md` | ui/theme |
| `testing` | `.claude/skills/testing/SKILL.md` | test |
| `i18n` | `.claude/skills/i18n/SKILL.md` | res/values |

### Auto-invoke Rules

#### SDD Workflow (Use Subagents)

| Action | Use This Subagent |
|--------|-------------------|
| Think through or explore a feature | `sdd-explore` |
| Create a change proposal | `sdd-propose` |
| Write specifications | `sdd-spec` |
| Create technical design | `sdd-design` |
| Break down into tasks | `sdd-tasks` |
| Implement tasks | `sdd-apply` |
| Verify implementation | `sdd-verify` |
| Archive completed change | `sdd-archive` |

#### Skill-Based Concerns (Read Skill First)

| Action | Read This Skill |
|--------|-----------------|
| Creating domain entities or value objects | `architecture` |
| Creating repository interfaces | `architecture` |
| Creating use cases | `architecture` |
| Adding a new domain model | `architecture` |
| Creating Room entity | `room-db` |
| Creating DAO | `room-db` |
| Writing TypeConverters | `room-db` |
| Modifying AppDatabase | `room-db` |
| Adding migrations | `room-db` |
| Creating Compose screens | `compose-android` |
| Creating ViewModels | `compose-android` |
| Wiring Hilt modules | `compose-android` |
| Navigation changes | `compose-android` |
| Creating/modifying themes | `theming` |
| Adding design tokens | `theming` |
| Writing unit tests | `testing` |
| Writing integration tests | `testing` |
| Creating fakes or test doubles | `testing` |
| Adding/modifying any UI string | `i18n` |
| Locale switching logic | `i18n` |

---

## Testing Rules

- **Use cases**: 100% coverage, unit tests
- **Repository**: integration tests against in-memory Room DB
- **ViewModels**: unit tests with Turbine for Flow assertions
- **Compose UI**: one test per screen — happy path with both themes
- **No garbage tests** — `assertTrue(true)` or empty assertions are rejected
- Every test must represent a realistic scenario
- All tests must be independent — no shared mutable state

---

## Git & Branch Strategy

- **NEVER** commit directly to `main`
- All work in feature branches: `feature/descriptive-name`
- Atomic commits with conventional format: `feat:`, `fix:`, `test:`, `refactor:`, `chore:`
- Validate build before every commit
- **COMMIT HANDOFF**: After implementing changes, present `git status` + `git diff --stat` and ask the user to commit.

---

## Decision Log

| Decision | Rationale |
|----------|-----------|
| Two theme families (Paper, Minimal) | Product requirement — curated aesthetics over Material You dynamic color |
| ThemeFamily × ThemeMode independent prefs | User controls look and brightness separately |
| Hot-swappable themes via DataStore | No activity restart needed; full recomposition on pref change |
| Room for local store | Standard Android SQLite ORM; no network dependency |
| DataStore for preferences | Replaces SharedPreferences; coroutine-native |
| Hilt for DI | Compile-time verified; standard Android DI |
| UUID for entry IDs | Sync-safe identifiers; no server round-trip for ID generation |
| Money as Long (minor units) | No floating-point errors; standard fintech approach |
| completedAt null = active | Single field determines active/done state; no separate boolean |
| column field drives layout | Moving an entry = one field update on one row |
| Per-app locale (Android 13+) | User controls locale independently of system locale |
| Supabase for sync (phase 2) | Realtime + Postgres + auth in one SDK; last-write-wins via updatedAt |

---

## Context Files

| File | When to Read |
|------|-------------|
| `AGENTS.md` | Always — this file |
| `PROJECT_STATUS.md` | Always — current state, pending tasks |
| `CHANGELOG.md` | Before merging — verify what changed |
| `.claude/skills/{name}/SKILL.md` | Before touching that concern area |

---

## Current Status

See [PROJECT_STATUS.md](PROJECT_STATUS.md) for detailed implementation progress.
