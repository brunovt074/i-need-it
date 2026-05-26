---
skill: theming
description: Two-theme system (Paper × Minimal) × three modes (Light/Dark/System) for i-need-it
scope: ui/theme
auto_invoke:
  - Creating or modifying themes
  - Adding design tokens
  - ThemeFamily or ThemeMode changes
  - Adding a new theme family
---

# Theming Skill — i-need-it

## Identity

You are a theming specialist for an Android Compose app with two independently-selectable design aesthetics. Your job is to ensure themes are hot-swappable at runtime, pixel-faithful to the design prototype, and architecturally clean (new theme = one file + one enum entry).

## The Model

Two **independent** user preferences stored in DataStore:

```kotlin
enum class ThemeFamily { PAPER, MINIMAL }
enum class ThemeMode   { LIGHT, DARK, SYSTEM }
```

The active `ColorScheme` is computed from `(family, mode, isSystemInDarkTheme)`.

## AppTokens — Custom Token System

`AppTokens` extends M3's `ColorScheme` with project-specific tokens not expressible in standard Material:

```kotlin
data class AppTokens(
    val colorScheme: ColorScheme,
    val typography: Typography,
    val shapes: Shapes,
    // Column accent colors
    val needSolid: Color,
    val needSoft: Color,
    val needWash: Color,
    val needBorder: Color,
    val wantSolid: Color,
    val wantSoft: Color,
    val wantWash: Color,
    val wantBorder: Color,
    val wishSolid: Color,
    val wishSoft: Color,
    val wishWash: Color,
    val wishBorder: Color,
    // Ink scale
    val inkStrong: Color,
    val inkDefault: Color,
    val inkSoft: Color,
    val inkFaded: Color,
    val inkWhisper: Color,
    // Background
    val paperBackground: Color,
    val paperDots: Boolean,      // true for paper theme, false for minimal
)

val LocalAppTokens = compositionLocalOf<AppTokens> { error("No AppTokens provided") }
```

## AppTheme Composable

```kotlin
@Composable
fun AppTheme(
    themePrefs: ThemePrefs,
    content: @Composable () -> Unit,
) {
    val family by themePrefs.family.collectAsStateWithLifecycle(initialValue = ThemeFamily.PAPER)
    val mode   by themePrefs.mode.collectAsStateWithLifecycle(initialValue = ThemeMode.SYSTEM)

    val isDark = when (mode) {
        ThemeMode.LIGHT  -> false
        ThemeMode.DARK   -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val tokens = remember(family, isDark) { tokensFor(family, isDark) }

    MaterialTheme(
        colorScheme = tokens.colorScheme,
        typography  = tokens.typography,
        shapes      = tokens.shapes,
    ) {
        CompositionLocalProvider(LocalAppTokens provides tokens) {
            content()
        }
    }
}

fun tokensFor(family: ThemeFamily, isDark: Boolean): AppTokens = when (family) {
    ThemeFamily.PAPER   -> if (isDark) PaperDarkTokens   else PaperLightTokens
    ThemeFamily.MINIMAL -> if (isDark) MinimalDarkTokens else MinimalLightTokens
}
```

## Design Token Values

### Paper · Light

```kotlin
val PaperLightTokens = AppTokens(
    colorScheme = lightColorScheme(
        background       = Color(0xFFF1E8D4),
        surface          = Color(0xFFF1E8D4),
        surfaceVariant   = Color(0xFFEBE0C5),
        outlineVariant   = Color(0xFFD8C9A8),
        onSurface        = Color(0xFF7A2E1F),
        onSurfaceVariant = Color(0xFF7A2E1F),
        primary          = Color(0xFF8A2818),
        onPrimary        = Color(0xFFF1E8D4),
        error            = Color(0xFFB8861E),
        // fill remaining M3 roles with sensible Paper values
    ),
    typography = PaperTypography,
    shapes     = PaperShapes,
    needSolid  = Color(0xFF8A2818),
    needSoft   = Color(0xC78A2818),
    needWash   = Color(0x108A2818),
    needBorder = Color(0x528A2818),
    wantSolid  = Color(0xFFA04A37),
    wantSoft   = Color(0xC7A04A37),
    wantWash   = Color(0x0DA04A37),
    wantBorder = Color(0x47A04A37),
    wishSolid  = Color(0xFFB6735A),
    wishSoft   = Color(0xC7B6735A),
    wishWash   = Color(0x0DB6735A),
    wishBorder = Color(0x42B6735A),
    inkStrong  = Color(0xFF5B1D10),
    inkDefault = Color(0xFF7A2E1F),
    inkSoft    = Color(0xB87A2E1F),
    inkFaded   = Color(0x737A2E1F),
    inkWhisper = Color(0x2E7A2E1F),
    paperBackground = Color(0xFFF1E8D4),
    paperDots  = true,
)
```

### Paper · Dark

```kotlin
val PaperDarkTokens = AppTokens(
    colorScheme = darkColorScheme(
        background       = Color(0xFF1D1611),
        surface          = Color(0xFF1D1611),
        surfaceVariant   = Color(0xFF241C15),
        outlineVariant   = Color(0xFF382A1F),
        onSurface        = Color(0xFFECD2B2),
        onSurfaceVariant = Color(0xFFECD2B2),
        primary          = Color(0xFFE88F6F),
        onPrimary        = Color(0xFF1D1611),
        error            = Color(0xFFB8861E),
    ),
    typography = PaperTypography,
    shapes     = PaperShapes,
    needSolid  = Color(0xFFE88F6F),
    needSoft   = Color(0xD1E88F6F),
    needWash   = Color(0x14E88F6F),
    needBorder = Color(0x52E88F6F),
    wantSolid  = Color(0xFFD9A583),
    wantSoft   = Color(0xD1D9A583),
    wantWash   = Color(0x0FD9A583),
    wantBorder = Color(0x47D9A583),
    wishSolid  = Color(0xFFC5B394),
    wishSoft   = Color(0xD1C5B394),
    wishWash   = Color(0x0DC5B394),
    wishBorder = Color(0x42C5B394),
    inkStrong  = Color(0xFFF5E3C8),
    inkDefault = Color(0xFFECD2B2),
    inkSoft    = Color(0xC7ECD2B2),
    inkFaded   = Color(0x80ECD2B2),
    inkWhisper = Color(0x38ECD2B2),
    paperBackground = Color(0xFF1D1611),
    paperDots  = true,
)
```

### Minimal · Light

```kotlin
val MinimalLightTokens = AppTokens(
    colorScheme = lightColorScheme(
        background       = Color(0xFFFBFAF8),
        surface          = Color(0xFFFBFAF8),
        surfaceVariant   = Color(0xFFF4F2EF),
        outlineVariant   = Color(0xFFDDD8D2),
        onSurface        = Color(0xFF1C1B17),
        onSurfaceVariant = Color(0xFF1C1B17),
        primary          = Color(0xFF8B3A2E),
        onPrimary        = Color(0xFFFBFAF8),
        error            = Color(0xFF9C7800),
    ),
    typography = MinimalTypography,
    shapes     = MinimalShapes,
    needSolid  = Color(0xFF8B3A2E),
    needSoft   = Color(0xC78B3A2E),
    needWash   = Color(0x108B3A2E),
    needBorder = Color(0x528B3A2E),
    wantSolid  = Color(0xFF6B5544),
    wantSoft   = Color(0xC76B5544),
    wantWash   = Color(0x0D6B5544),
    wantBorder = Color(0x476B5544),
    wishSolid  = Color(0xFF8E847A),
    wishSoft   = Color(0xC78E847A),
    wishWash   = Color(0x0D8E847A),
    wishBorder = Color(0x428E847A),
    inkStrong  = Color(0xFF0A0A08),
    inkDefault = Color(0xFF1C1B17),
    inkSoft    = Color(0xB81C1B17),
    inkFaded   = Color(0x731C1B17),
    inkWhisper = Color(0x2E1C1B17),
    paperBackground = Color(0xFFFBFAF8),
    paperDots  = false,
)
```

### Minimal · Dark

```kotlin
val MinimalDarkTokens = AppTokens(
    colorScheme = darkColorScheme(
        background       = Color(0xFF141311),
        surface          = Color(0xFF141311),
        surfaceVariant   = Color(0xFF1C1B18),
        outlineVariant   = Color(0xFF353330),
        onSurface        = Color(0xFFEDE9E2),
        onSurfaceVariant = Color(0xFFEDE9E2),
        primary          = Color(0xFFFFB59E),
        onPrimary        = Color(0xFF141311),
        error            = Color(0xFF9C7800),
    ),
    typography = MinimalTypography,
    shapes     = MinimalShapes,
    needSolid  = Color(0xFFFFB59E),
    needSoft   = Color(0xD1FFB59E),
    needWash   = Color(0x14FFB59E),
    needBorder = Color(0x52FFB59E),
    wantSolid  = Color(0xFFE7C4A7),
    wantSoft   = Color(0xD1E7C4A7),
    wantWash   = Color(0x0FE7C4A7),
    wantBorder = Color(0x47E7C4A7),
    wishSolid  = Color(0xFFCAC0B4),
    wishSoft   = Color(0xD1CAC0B4),
    wishWash   = Color(0x0DCAC0B4),
    wishBorder = Color(0x42CAC0B4),
    inkStrong  = Color(0xFFF5F2EC),
    inkDefault = Color(0xFFEDE9E2),
    inkSoft    = Color(0xC7EDE9E2),
    inkFaded   = Color(0x80EDE9E2),
    inkWhisper = Color(0x38EDE9E2),
    paperBackground = Color(0xFF141311),
    paperDots  = false,
)
```

## Typography

```kotlin
val PaperTypography = Typography(
    displayLarge  = TextStyle(fontFamily = NewsreaderFamily, fontWeight = FontWeight.Medium, fontSize = 36.sp, letterSpacing = (-0.02).em),
    titleLarge    = TextStyle(fontFamily = NewsreaderFamily, fontWeight = FontWeight.Medium, fontSize = 22.sp, letterSpacing = (-0.01).em),
    bodyLarge     = TextStyle(fontFamily = GeistFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp),
    bodyMedium    = TextStyle(fontFamily = GeistFamily, fontWeight = FontWeight.Normal, fontSize = 14.sp),
    labelMedium   = TextStyle(fontFamily = GeistFamily, fontWeight = FontWeight.Medium, fontSize = 12.sp),
)

val MinimalTypography = Typography(
    displayLarge  = TextStyle(fontFamily = GeistFamily, fontWeight = FontWeight.SemiBold, fontSize = 36.sp),
    titleLarge    = TextStyle(fontFamily = GeistFamily, fontWeight = FontWeight.SemiBold, fontSize = 22.sp),
    bodyLarge     = TextStyle(fontFamily = GeistFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp),
    bodyMedium    = TextStyle(fontFamily = GeistFamily, fontWeight = FontWeight.Normal, fontSize = 14.sp),
    labelMedium   = TextStyle(fontFamily = GeistFamily, fontWeight = FontWeight.Medium, fontSize = 12.sp),
)
```

## Shapes

```kotlin
val PaperShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small      = RoundedCornerShape(4.dp),
    medium     = RoundedCornerShape(8.dp),
    large      = RoundedCornerShape(14.dp),
    extraLarge = RoundedCornerShape(20.dp),
)

val MinimalShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small      = RoundedCornerShape(4.dp),
    medium     = RoundedCornerShape(12.dp),
    large      = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(28.dp),
)
```

## Motion

```kotlin
object Motion {
    val easeOut      = CubicBezierEasing(0.2f, 0.7f, 0.3f, 1f)
    val easeSpring   = CubicBezierEasing(0.34f, 1.56f, 0.64f, 1f)
    val easeStandard = CubicBezierEasing(0.2f, 0f, 0f, 1f)

    const val fast    = 140
    const val regular = 220
    const val slow    = 380
}
```

## Adding a Third Theme Family

To add e.g. `RETRO`:
1. Add `RETRO` to `ThemeFamily` enum
2. Create `RetroTheme.kt` with `RetroLightTokens` and `RetroDarkTokens`
3. Add two branches in `tokensFor()`:
   ```kotlin
   ThemeFamily.RETRO -> if (isDark) RetroDarkTokens else RetroLightTokens
   ```
4. Add a preview card in `ThemeCardPicker` composable

That is the complete change. No other files need modification.

## DataStore Preferences

```kotlin
class ThemePrefs @Inject constructor(@ApplicationContext context: Context) {
    private val store = context.createDataStore("theme_prefs")

    val family: Flow<ThemeFamily> = store.data.map { prefs ->
        prefs[FAMILY_KEY]?.let { ThemeFamily.valueOf(it) } ?: ThemeFamily.PAPER
    }

    val mode: Flow<ThemeMode> = store.data.map { prefs ->
        prefs[MODE_KEY]?.let { ThemeMode.valueOf(it) } ?: ThemeMode.SYSTEM
    }

    suspend fun setFamily(family: ThemeFamily) {
        store.edit { it[FAMILY_KEY] = family.name }
    }

    suspend fun setMode(mode: ThemeMode) {
        store.edit { it[MODE_KEY] = mode.name }
    }

    companion object {
        private val FAMILY_KEY = stringPreferencesKey("theme_family")
        private val MODE_KEY   = stringPreferencesKey("theme_mode")
    }
}
```
