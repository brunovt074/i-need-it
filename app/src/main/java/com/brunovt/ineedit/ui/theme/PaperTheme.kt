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
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

val NewsreaderFamily = FontFamily.Serif

val PaperTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = NewsreaderFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 36.sp,
        letterSpacing = (-0.02).em,
    ),
    titleLarge = TextStyle(
        fontFamily = NewsreaderFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        letterSpacing = (-0.01).em,
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
    ),
)

val PaperShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(14.dp),
    extraLarge = RoundedCornerShape(20.dp),
)

val PaperLightTokens = AppTokens(
    colorScheme = lightColorScheme(
        background = Color(0xFFF1E8D4),
        surface = Color(0xFFF1E8D4),
        surfaceVariant = Color(0xFFEBE0C5),
        outlineVariant = Color(0xFFD8C9A8),
        onSurface = Color(0xFF7A2E1F),
        onSurfaceVariant = Color(0xFF7A2E1F),
        primary = Color(0xFF8A2818),
        onPrimary = Color(0xFFF1E8D4),
        error = Color(0xFFB8861E),
        onError = Color(0xFFF1E8D4),
        errorContainer = Color(0xFFFFE0B2),
        onErrorContainer = Color(0xFF5D3A00),
        primaryContainer = Color(0xFFFFDAD6),
        onPrimaryContainer = Color(0xFF410002),
        secondary = Color(0xFFA04A37),
        onSecondary = Color(0xFFF1E8D4),
        secondaryContainer = Color(0xFFFFDAD6),
        onSecondaryContainer = Color(0xFF410002),
        tertiary = Color(0xFFB6735A),
        onTertiary = Color(0xFFF1E8D4),
        tertiaryContainer = Color(0xFFFFDBCF),
        onTertiaryContainer = Color(0xFF3A0B00),
        outline = Color(0xFFD8C9A8),
        surfaceTint = Color(0xFF8A2818),
    ),
    typography = PaperTypography,
    shapes = PaperShapes,
    needSolid = Color(0xFF8A2818),
    needSoft = Color(0xC78A2818),
    needWash = Color(0x108A2818),
    needBorder = Color(0x528A2818),
    wantSolid = Color(0xFFA04A37),
    wantSoft = Color(0xC7A04A37),
    wantWash = Color(0x0DA04A37),
    wantBorder = Color(0x47A04A37),
    wishSolid = Color(0xFFB6735A),
    wishSoft = Color(0xC7B6735A),
    wishWash = Color(0x0DB6735A),
    wishBorder = Color(0x42B6735A),
    inkStrong = Color(0xFF5B1D10),
    inkDefault = Color(0xFF7A2E1F),
    inkSoft = Color(0xB87A2E1F),
    inkFaded = Color(0x737A2E1F),
    inkWhisper = Color(0x2E7A2E1F),
    paperBackground = Color(0xFFF1E8D4),
    paperDots = true,
)

val PaperDarkTokens = AppTokens(
    colorScheme = darkColorScheme(
        background = Color(0xFF1D1611),
        surface = Color(0xFF1D1611),
        surfaceVariant = Color(0xFF241C15),
        outlineVariant = Color(0xFF382A1F),
        onSurface = Color(0xFFECD2B2),
        onSurfaceVariant = Color(0xFFECD2B2),
        primary = Color(0xFFE88F6F),
        onPrimary = Color(0xFF1D1611),
        error = Color(0xFFB8861E),
        onError = Color(0xFF1D1611),
        errorContainer = Color(0xFF4A3800),
        onErrorContainer = Color(0xFFFFE0B2),
        primaryContainer = Color(0xFF690300),
        onPrimaryContainer = Color(0xFFFFDAD6),
        secondary = Color(0xFFD9A583),
        onSecondary = Color(0xFF1D1611),
        secondaryContainer = Color(0xFF5E2118),
        onSecondaryContainer = Color(0xFFFFDAD6),
        tertiary = Color(0xFFC5B394),
        onTertiary = Color(0xFF1D1611),
        tertiaryContainer = Color(0xFF4A2618),
        onTertiaryContainer = Color(0xFFFFDBCF),
        outline = Color(0xFF382A1F),
        surfaceTint = Color(0xFFE88F6F),
    ),
    typography = PaperTypography,
    shapes = PaperShapes,
    needSolid = Color(0xFFE88F6F),
    needSoft = Color(0xD1E88F6F),
    needWash = Color(0x14E88F6F),
    needBorder = Color(0x52E88F6F),
    wantSolid = Color(0xFFD9A583),
    wantSoft = Color(0xD1D9A583),
    wantWash = Color(0x0FD9A583),
    wantBorder = Color(0x47D9A583),
    wishSolid = Color(0xFFC5B394),
    wishSoft = Color(0xD1C5B394),
    wishWash = Color(0x0DC5B394),
    wishBorder = Color(0x42C5B394),
    inkStrong = Color(0xFFF5E3C8),
    inkDefault = Color(0xFFECD2B2),
    inkSoft = Color(0xC7ECD2B2),
    inkFaded = Color(0x80ECD2B2),
    inkWhisper = Color(0x38ECD2B2),
    paperBackground = Color(0xFF1D1611),
    paperDots = true,
)
