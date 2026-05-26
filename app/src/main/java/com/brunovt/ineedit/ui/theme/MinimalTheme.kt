package com.brunovt.ineedit.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val GeistFamily = FontFamily.SansSerif
val CaveatFamily = FontFamily.Cursive

val MinimalTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = GeistFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 36.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = GeistFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = GeistFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = GeistFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = GeistFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
    ),
)

val MinimalShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(28.dp),
)

val MinimalLightTokens = AppTokens(
    colorScheme = lightColorScheme(
        background = Color(0xFFFBFAF8),
        surface = Color(0xFFFBFAF8),
        surfaceVariant = Color(0xFFF4F2EF),
        outlineVariant = Color(0xFFDDD8D2),
        onSurface = Color(0xFF1C1B17),
        onSurfaceVariant = Color(0xFF1C1B17),
        primary = Color(0xFF8B3A2E),
        onPrimary = Color(0xFFFBFAF8),
        error = Color(0xFF9C7800),
        onError = Color(0xFFFBFAF8),
        errorContainer = Color(0xFFFFF3CD),
        onErrorContainer = Color(0xFF3D2F00),
        primaryContainer = Color(0xFFFFDAD6),
        onPrimaryContainer = Color(0xFF410002),
        secondary = Color(0xFF6B5544),
        onSecondary = Color(0xFFFBFAF8),
        secondaryContainer = Color(0xFFEFDDD5),
        onSecondaryContainer = Color(0xFF271008),
        tertiary = Color(0xFF8E847A),
        onTertiary = Color(0xFFFBFAF8),
        tertiaryContainer = Color(0xFFE5DDD8),
        onTertiaryContainer = Color(0xFF1F1A17),
        outline = Color(0xFFDDD8D2),
        surfaceTint = Color(0xFF8B3A2E),
    ),
    typography = MinimalTypography,
    shapes = MinimalShapes,
    needSolid = Color(0xFF8B3A2E),
    needSoft = Color(0xC78B3A2E),
    needWash = Color(0x108B3A2E),
    needBorder = Color(0x528B3A2E),
    wantSolid = Color(0xFF6B5544),
    wantSoft = Color(0xC76B5544),
    wantWash = Color(0x0D6B5544),
    wantBorder = Color(0x476B5544),
    wishSolid = Color(0xFF8E847A),
    wishSoft = Color(0xC78E847A),
    wishWash = Color(0x0D8E847A),
    wishBorder = Color(0x428E847A),
    inkStrong = Color(0xFF0A0A08),
    inkDefault = Color(0xFF1C1B17),
    inkSoft = Color(0xB81C1B17),
    inkFaded = Color(0x731C1B17),
    inkWhisper = Color(0x2E1C1B17),
    paperBackground = Color(0xFFFBFAF8),
    paperDots = false,
)

val MinimalDarkTokens = AppTokens(
    colorScheme = darkColorScheme(
        background = Color(0xFF141311),
        surface = Color(0xFF141311),
        surfaceVariant = Color(0xFF1C1B18),
        outlineVariant = Color(0xFF353330),
        onSurface = Color(0xFFEDE9E2),
        onSurfaceVariant = Color(0xFFEDE9E2),
        primary = Color(0xFFFFB59E),
        onPrimary = Color(0xFF141311),
        error = Color(0xFF9C7800),
        onError = Color(0xFF141311),
        errorContainer = Color(0xFF3D2F00),
        onErrorContainer = Color(0xFFFFF3CD),
        primaryContainer = Color(0xFF690300),
        onPrimaryContainer = Color(0xFFFFDAD6),
        secondary = Color(0xFFE7C4A7),
        onSecondary = Color(0xFF141311),
        secondaryContainer = Color(0xFF52402F),
        onSecondaryContainer = Color(0xFFEFDDD5),
        tertiary = Color(0xFFCAC0B4),
        onTertiary = Color(0xFF141311),
        tertiaryContainer = Color(0xFF3A342F),
        onTertiaryContainer = Color(0xFFE5DDD8),
        outline = Color(0xFF353330),
        surfaceTint = Color(0xFFFFB59E),
    ),
    typography = MinimalTypography,
    shapes = MinimalShapes,
    needSolid = Color(0xFFFFB59E),
    needSoft = Color(0xD1FFB59E),
    needWash = Color(0x14FFB59E),
    needBorder = Color(0x52FFB59E),
    wantSolid = Color(0xFFE7C4A7),
    wantSoft = Color(0xD1E7C4A7),
    wantWash = Color(0x0FE7C4A7),
    wantBorder = Color(0x47E7C4A7),
    wishSolid = Color(0xFFCAC0B4),
    wishSoft = Color(0xD1CAC0B4),
    wishWash = Color(0x0DCAC0B4),
    wishBorder = Color(0x42CAC0B4),
    inkStrong = Color(0xFFF5F2EC),
    inkDefault = Color(0xFFEDE9E2),
    inkSoft = Color(0xC7EDE9E2),
    inkFaded = Color(0x80EDE9E2),
    inkWhisper = Color(0x38EDE9E2),
    paperBackground = Color(0xFF141311),
    paperDots = false,
)
