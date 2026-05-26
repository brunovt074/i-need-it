package com.brunovt.ineedit.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brunovt.ineedit.data.prefs.ThemeFamily
import com.brunovt.ineedit.data.prefs.ThemeMode
import com.brunovt.ineedit.data.prefs.ThemePrefs

@Composable
fun AppTheme(
    themePrefs: ThemePrefs,
    content: @Composable () -> Unit,
) {
    val family by themePrefs.family.collectAsStateWithLifecycle(initialValue = ThemeFamily.PAPER)
    val mode by themePrefs.mode.collectAsStateWithLifecycle(initialValue = ThemeMode.SYSTEM)

    val isDark = when (mode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val tokens = remember(family, isDark) { tokensFor(family, isDark) }

    MaterialTheme(
        colorScheme = tokens.colorScheme,
        typography = tokens.typography,
        shapes = tokens.shapes,
    ) {
        CompositionLocalProvider(LocalAppTokens provides tokens) {
            content()
        }
    }
}

fun tokensFor(family: ThemeFamily, isDark: Boolean): AppTokens = when (family) {
    ThemeFamily.PAPER -> if (isDark) PaperDarkTokens else PaperLightTokens
    ThemeFamily.MINIMAL -> if (isDark) MinimalDarkTokens else MinimalLightTokens
}
