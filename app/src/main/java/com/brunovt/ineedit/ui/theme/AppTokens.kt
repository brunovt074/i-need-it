package com.brunovt.ineedit.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppTokens(
    val colorScheme: ColorScheme,
    val typography: Typography,
    val shapes: Shapes,
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
    val inkStrong: Color,
    val inkDefault: Color,
    val inkSoft: Color,
    val inkFaded: Color,
    val inkWhisper: Color,
    val paperBackground: Color,
    val paperDots: Boolean,
)

val LocalAppTokens = compositionLocalOf<AppTokens> { error("No AppTokens provided") }
