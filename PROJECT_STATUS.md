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

## Known Issues

None yet — project not built.

## Dependencies

See `gradle/libs.versions.toml` for full version catalog.
