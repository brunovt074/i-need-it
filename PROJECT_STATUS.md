# i-need-it — Project Status

## Current Phase: Phase 1 — Local MVP (implemented, pending first build)

## Phase 1 Checklist

- [x] Project scaffold: Gradle KTS, version catalogs, Hilt, Room, DataStore, Navigation, Compose BoM
- [x] Themes: `AppTheme`, Paper × light/dark/system, Minimal × light/dark/system, hot-swappable
- [x] i18n: `values/strings.xml` + `values-es/strings.xml` from JSON dictionaries; per-app locale
- [x] Data: Room schema, `EntryDao`, `EntryRepository`, seed fixture (debug only)
- [x] Dashboard — accordion layout, item list, tap-to-open form
- [x] Item form — create / view / edit, bottom sheet, all fields
- [x] Settings — language, theme style, mode, sync placeholder
- [x] Done history screen + restore action
- [x] Layout switcher (accordion ⇄ fan)
- [ ] Search across active items (top bar icon → full-screen search overlay)

## Phase 2 Checklist (not started)

- [ ] Supabase auth (email + magic link)
- [ ] Postgres `entries` table mirroring `EntryEntity`
- [ ] Last-write-wins reconciliation using `updatedAt`
- [ ] Supabase Realtime channel
- [ ] Sync status in Settings + top bar indicator

## Known Build Requirements

- **No `gradlew` binary present** — run `gradle wrapper --gradle-version=8.9` once, or open in Android Studio (generates the wrapper automatically).
- **Fonts**: Using system fallbacks (`FontFamily.Serif` = Newsreader, `FontFamily.SansSerif` = Geist, `FontFamily.Cursive` = Caveat). Add real TTF files to `res/font/` for production.
- **App icon**: No mipmap resources yet — add before release.
- **`fallbackToDestructiveMigration()`** in `DatabaseModule` — remove before production and add proper migrations.
- **KSP version**: `2.1.0-1.0.29` required for Kotlin 2.1.0. If unavailable, try `2.1.0-1.0.28`.

## Performance Fixes Applied (branch: fix/performance-issues)

### Critical — latency botones guardar/cancelar
- [x] `App.kt` — eliminado `runBlocking` en `onCreate`; locale default inmediato + corrección async
- [x] `AndroidManifest.xml` — agregado `android:autoStoreLocales="true"`
- [x] `LocalePrefs.kt` — constante `DEFAULT_LOCALE = "en"`
- [x] `EntryFormViewModel.save()` — eliminado `onDismiss` callback; `isSaving=true` durante save, `savedSuccessfully=true` al completar
- [x] `EntryFormSheet.kt` — `onSave = viewModel::save` (sin callback)
- [x] `ItemForm.kt` — cancel button tiene `enabled = !state.isSaving`

### Critical — performance de datos
- [x] `RoomEntryRepository` — `tagsMap`/`statusesMap` cacheados como `StateFlow` (SharingStarted.Eagerly)
- [x] `RoomEntryRepository` — `byId` usa los maps cacheados (no más `dao.all()` por cada apertura)
- [x] `RoomEntryRepository` — `.distinctUntilChanged()` + `.flowOn(Dispatchers.Default)` en observe flows
- [x] `RoomEntryRepository.upsert/delete` — `withContext(Dispatchers.IO)` para JSON encode off-main
- [x] `di/AppModule.kt` + `di/AppScope.kt` — `@AppScope` qualifier con `CoroutineScope(SupervisorJob + IO)`

### Dashboard performance
- [x] `AccordionLayout.kt` — `Column+forEachIndexed` → `LazyColumn + itemsIndexed(key = { it.id })`
- [x] `FanLayout.kt` — `LaunchedEffect(pagerState)` → `LaunchedEffect(Unit)` (snapshotFlow ya trackea cambios)
- [x] `DashboardViewModel.kt` — 3 collect manuales → `stateIn + distinctUntilChanged` x3
- [x] `DashboardScreen.kt` — lambdas memoizadas con `remember`
- [x] `DoneScreen.kt` — `items(items)` → `items(items, key = { it.id })`

### Build / Release
- [x] `app/build.gradle.kts` — `isMinifyEnabled = true`, `isShrinkResources = true`
- [x] `app/build.gradle.kts` — `composeCompiler { reportsDestination, metricsDestination }`
- [x] `app/build.gradle.kts` — dependencias `kotlinx-collections-immutable`, `profileinstaller`
- [x] `app/proguard-rules.pro` — rules para kotlinx-serialization, Room, Hilt, Compose
- [x] `gradle/libs.versions.toml` — version bumps (Compose BOM, Kotlin, KSP, Hilt, Room, etc.)

### Tests TDD
- [x] `EntryFormViewModelTest.kt` — verifica transiciones de `isSaving`/`savedSuccessfully`
- [x] `FakeTagRepository.kt` + `FakeStatusRepository.kt` — test doubles

### Benchmark (requiere device/emulator para ejecutar)
- [x] Módulo `:benchmark` creado (`benchmark/`)
- [x] `ColdStartupBenchmark` — mide startup time cold
- [x] `EntryFormSaveBenchmark` — mide frame timing de guardar
- [x] `EntryFormCancelBenchmark` — mide frame timing de cancelar
- [x] `DashboardScrollBenchmark` — mide scroll del accordion
- [x] `BaselineProfileGenerator` — genera `baseline-prof.txt`

### Pasos manuales pendientes (requieren device/emulator)
```bash
# Compilar y correr tests unitarios
./gradlew test

# Correr benchmarks (requiere device conectado en release mode)
./gradlew :benchmark:connectedBenchmarkAndroidTest

# Generar baseline profile (requiere device)
./gradlew :benchmark:generateBaselineProfile

# Verificar Compose compiler reports tras build
find app/build/compose_compiler -name '*-classes.txt'

# Verificar tamaño APK release vs debug
./gradlew assembleRelease assembleDebug
ls -lh app/build/outputs/apk/*/app-*.apk
```

### Importante: verificar version bumps
Los bumps en `libs.versions.toml` son agresivos (todas las libs). Si el build falla por incompatibilidad:
1. Verificar KSP version = `{kotlin-version}-{ksp-minor}` (ej: `2.1.20-2.0.1`)
2. Si Hilt/Room rompen, revertir a la versión anterior de la lib específica
3. Compose BOM y Kotlin deben mantenerse en sync

## Known Issues

None yet — project not built.

## Dependencies

See `gradle/libs.versions.toml` for full version catalog.
