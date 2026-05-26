---
skill: i18n
description: i18n contract for i-need-it — strings.xml (es/en), no hardcoded strings, locale switching
scope: res/values, ui
auto_invoke:
  - Adding any user-facing label, message, or text
  - Modifying existing strings
  - Locale switching logic
  - Settings screen language section
---

# i18n Skill — i-need-it

## Identity

You are the i18n guardian for this project. Your absolute rule: **no user-facing string is ever hardcoded in Kotlin or Compose files**. Every label, message, placeholder, and error must flow through `stringResource(R.string.…)`.

## The Contract

1. Every string lives in **both** `values/strings.xml` (English) and `values-es/strings.xml` (Spanish)
2. Every UI string is accessed via `stringResource(R.string.key)` or the `t()` helper
3. Adding ANY label requires touching both files in the same commit
4. PR description must list the keys added

## Layout

```
app/src/main/res/
  values/strings.xml        ← English (default)
  values-es/strings.xml     ← Spanish
```

## Helper Function

```kotlin
@Composable
fun t(@StringRes id: Int, vararg args: Any): String =
    if (args.isEmpty()) stringResource(id)
    else stringResource(id, *args)
```

Use `t(R.string.columns_need_title)` everywhere. One import, consistent call site.

## Key Naming Convention

JSON path → Android key (dots → underscores):

| JSON path | Android key |
|-----------|-------------|
| `columns.need.title` | `columns_need_title` |
| `columns.need.promptCta` | `columns_need_prompt_cta` |
| `columns.need.addCta` | `columns_need_add_cta` |
| `columns.need.empty` | `columns_need_empty` |
| `form.nameLabel` | `form_name_label` |
| `form.namePlaceholder` | `form_name_placeholder` |
| `time.thisWeek` | `time_this_week` |
| `history.completedOn` `{date}` | `history_completed_on` |
| `settings.title` | `settings_title` |

Interpolated tokens → positional args:
```xml
<string name="history_completed_on">Done on %1$s</string>
```
```kotlin
t(R.string.history_completed_on, formattedDate)
```

## Complete Key Set (from JSON dictionaries)

### English (`values/strings.xml`)

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- App -->
    <string name="app_name">i-need-it</string>
    <string name="app_tagline">Three columns to organize your life</string>

    <!-- Columns -->
    <string name="columns_need_title">I need</string>
    <string name="columns_need_subtitle">Urgent · today to 2 weeks</string>
    <string name="columns_need_prompt_cta">What do you need?</string>
    <string name="columns_need_add_cta">Add to I need</string>
    <string name="columns_need_empty">Nothing urgent right now</string>

    <string name="columns_want_title">I want</string>
    <string name="columns_want_subtitle">Mid-term · 2 weeks to 6 months</string>
    <string name="columns_want_prompt_cta">What do you want?</string>
    <string name="columns_want_add_cta">Add to I want</string>
    <string name="columns_want_empty">No mid-term goals yet</string>

    <string name="columns_wish_title">I wish</string>
    <string name="columns_wish_subtitle">Long-term · 6 months+</string>
    <string name="columns_wish_prompt_cta">What do you wish for?</string>
    <string name="columns_wish_add_cta">Add to I wish</string>
    <string name="columns_wish_empty">No dreams logged yet</string>

    <!-- Form -->
    <string name="form_name_label">Name</string>
    <string name="form_name_placeholder">Write what it is…</string>
    <string name="form_time_label">When?</string>
    <string name="form_cost_label">Cost</string>
    <string name="form_place_label">Where?</string>
    <string name="form_place_placeholder">e.g. hardware store, online, gym…</string>
    <string name="form_tags_label">Tags</string>
    <string name="form_required">required</string>
    <string name="form_optional">optional</string>
    <string name="form_save">Save</string>
    <string name="form_cancel">Cancel</string>
    <string name="form_delete">Delete</string>
    <string name="form_complete">Mark as done</string>
    <string name="form_edit">Edit</string>
    <string name="form_move_to">Move to</string>

    <!-- Time -->
    <string name="time_today">Today</string>
    <string name="time_this_week">This week</string>
    <string name="time_two_weeks">2 weeks</string>
    <string name="time_this_month">This month</string>
    <string name="time_three_months">3 months</string>
    <string name="time_six_months">6 months</string>
    <string name="time_one_year">1 year</string>
    <string name="time_more_than_year">1 year+</string>
    <string name="time_specific_date">Exact date</string>
    <string name="time_pick_date">Pick a date</string>

    <!-- Tags -->
    <string name="tags_health">Health</string>
    <string name="tags_home">Home</string>
    <string name="tags_hobby">Hobby</string>
    <string name="tags_work">Work</string>
    <string name="tags_family">Family</string>
    <string name="tags_travel">Travel</string>
    <string name="tags_money">Finance</string>
    <string name="tags_study">Study</string>
    <string name="tags_fitness">Fitness</string>

    <!-- Search -->
    <string name="search_placeholder">Search…</string>
    <string name="search_no_results">Nothing found</string>

    <!-- Settings -->
    <string name="settings_title">Settings</string>
    <string name="settings_language">Language</string>
    <string name="settings_language_es">Español</string>
    <string name="settings_language_en">English</string>
    <string name="settings_theme">Theme</string>
    <string name="settings_theme_light">Light</string>
    <string name="settings_theme_dark">Dark</string>
    <string name="settings_theme_auto">Auto</string>
    <string name="settings_theme_paper">Paper</string>
    <string name="settings_theme_minimal">Minimal</string>
    <string name="settings_sync">Sync</string>
    <string name="settings_synced">Synced</string>
    <string name="settings_syncing">Syncing…</string>
    <string name="settings_offline">Offline · saved locally</string>
    <string name="settings_account">Account</string>
    <string name="settings_sign_out">Sign out</string>
    <string name="settings_about">About</string>
    <string name="settings_version">Version</string>

    <!-- History -->
    <string name="history_title">Done</string>
    <string name="history_empty">Nothing completed yet</string>
    <string name="history_completed_on">Done on %1$s</string>

    <!-- Actions -->
    <string name="action_add">Add</string>
    <string name="action_filter">Filter</string>
    <string name="action_search">Search</string>
    <string name="action_back">Back</string>
    <string name="action_close">Close</string>
    <string name="action_done">Done</string>
    <string name="action_layout_list">List view</string>
    <string name="action_layout_fan">Fan view</string>

    <!-- Nav -->
    <string name="nav_dashboard">Home</string>
    <string name="nav_history">Done</string>
    <string name="nav_settings">Settings</string>
</resources>
```

### Spanish (`values-es/strings.xml`)

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- App -->
    <string name="app_name">i-need-it</string>
    <string name="app_tagline">Tres columnas para organizar la vida</string>

    <!-- Columns -->
    <string name="columns_need_title">Necesito</string>
    <string name="columns_need_subtitle">Urgente · hoy a 2 semanas</string>
    <string name="columns_need_prompt_cta">¿Qué necesitas?</string>
    <string name="columns_need_add_cta">Agregar a Necesito</string>
    <string name="columns_need_empty">Nada urgente por ahora</string>

    <string name="columns_want_title">Quiero</string>
    <string name="columns_want_subtitle">Mediano plazo · 2 semanas a 6 meses</string>
    <string name="columns_want_prompt_cta">¿Qué quieres?</string>
    <string name="columns_want_add_cta">Agregar a Quiero</string>
    <string name="columns_want_empty">Sin objetivos a mediano plazo</string>

    <string name="columns_wish_title">Deseo</string>
    <string name="columns_wish_subtitle">Largo plazo · 6 meses+</string>
    <string name="columns_wish_prompt_cta">¿Qué deseas?</string>
    <string name="columns_wish_add_cta">Agregar a Deseo</string>
    <string name="columns_wish_empty">Sin sueños cargados todavía</string>

    <!-- Form -->
    <string name="form_name_label">Nombre</string>
    <string name="form_name_placeholder">Escribí qué es…</string>
    <string name="form_time_label">¿Dentro de cuánto?</string>
    <string name="form_cost_label">Costo</string>
    <string name="form_place_label">¿Dónde?</string>
    <string name="form_place_placeholder">Ej. ferretería, online, gimnasio…</string>
    <string name="form_tags_label">Categorías</string>
    <string name="form_required">obligatorio</string>
    <string name="form_optional">opcional</string>
    <string name="form_save">Guardar</string>
    <string name="form_cancel">Cancelar</string>
    <string name="form_delete">Eliminar</string>
    <string name="form_complete">Marcar como hecho</string>
    <string name="form_edit">Editar</string>
    <string name="form_move_to">Mover a</string>

    <!-- Time -->
    <string name="time_today">Hoy</string>
    <string name="time_this_week">Esta semana</string>
    <string name="time_two_weeks">2 semanas</string>
    <string name="time_this_month">Este mes</string>
    <string name="time_three_months">3 meses</string>
    <string name="time_six_months">6 meses</string>
    <string name="time_one_year">1 año</string>
    <string name="time_more_than_year">+1 año</string>
    <string name="time_specific_date">Fecha exacta</string>
    <string name="time_pick_date">Elegir fecha</string>

    <!-- Tags -->
    <string name="tags_health">Salud</string>
    <string name="tags_home">Casa</string>
    <string name="tags_hobby">Hobby</string>
    <string name="tags_work">Trabajo</string>
    <string name="tags_family">Familia</string>
    <string name="tags_travel">Viaje</string>
    <string name="tags_money">Finanzas</string>
    <string name="tags_study">Estudio</string>
    <string name="tags_fitness">Fitness</string>

    <!-- Search -->
    <string name="search_placeholder">Buscar…</string>
    <string name="search_no_results">No se encontró nada</string>

    <!-- Settings -->
    <string name="settings_title">Ajustes</string>
    <string name="settings_language">Idioma</string>
    <string name="settings_language_es">Español</string>
    <string name="settings_language_en">English</string>
    <string name="settings_theme">Tema</string>
    <string name="settings_theme_light">Claro</string>
    <string name="settings_theme_dark">Oscuro</string>
    <string name="settings_theme_auto">Automático</string>
    <string name="settings_theme_paper">Paper</string>
    <string name="settings_theme_minimal">Minimal</string>
    <string name="settings_sync">Sincronización</string>
    <string name="settings_synced">Sincronizado</string>
    <string name="settings_syncing">Sincronizando…</string>
    <string name="settings_offline">Sin conexión · guardado local</string>
    <string name="settings_account">Cuenta</string>
    <string name="settings_sign_out">Cerrar sesión</string>
    <string name="settings_about">Acerca de</string>
    <string name="settings_version">Versión</string>

    <!-- History -->
    <string name="history_title">Hechos</string>
    <string name="history_empty">Aún nada completado</string>
    <string name="history_completed_on">Hecho el %1$s</string>

    <!-- Actions -->
    <string name="action_add">Agregar</string>
    <string name="action_filter">Filtrar</string>
    <string name="action_search">Buscar</string>
    <string name="action_back">Atrás</string>
    <string name="action_close">Cerrar</string>
    <string name="action_done">Listo</string>
    <string name="action_layout_list">Ver como lista</string>
    <string name="action_layout_fan">Ver como abanico</string>

    <!-- Nav -->
    <string name="nav_dashboard">Inicio</string>
    <string name="nav_history">Hechos</string>
    <string name="nav_settings">Ajustes</string>
</resources>
```

## Locale Switching

```kotlin
class LocalePrefs @Inject constructor(@ApplicationContext context: Context) {
    private val store = context.createDataStore("locale_prefs")

    val locale: Flow<String> = store.data.map { prefs ->
        prefs[LOCALE_KEY] ?: "es"
    }

    suspend fun setLocale(tag: String) {
        store.edit { it[LOCALE_KEY] = tag }
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(tag))
    }

    companion object {
        private val LOCALE_KEY = stringPreferencesKey("locale")
    }
}
```

Apply stored locale on app startup:
```kotlin
@HiltAndroidApp
class App : Application() {
    @Inject lateinit var localePrefs: LocalePrefs

    override fun onCreate() {
        super.onCreate()
        // Restore locale synchronously on startup
        runBlocking {
            val tag = localePrefs.locale.first()
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(tag))
        }
    }
}
```

## Lint Rule

In `lint.xml` or `build.gradle.kts`:
```xml
<issue id="HardcodedText" severity="error" />
```
This turns hardcoded text into a build error on release builds.

## Adding a New String — Checklist

- [ ] Add to `values/strings.xml` (English)
- [ ] Add to `values-es/strings.xml` (Spanish)
- [ ] Use via `stringResource(R.string.your_key)` or `t(R.string.your_key)`
- [ ] List both files in PR description
