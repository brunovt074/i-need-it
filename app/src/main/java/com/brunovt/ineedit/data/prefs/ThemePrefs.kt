package com.brunovt.ineedit.data.prefs

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

enum class ThemeFamily { PAPER, MINIMAL }
enum class ThemeMode { LIGHT, DARK, SYSTEM }

private val Context.themeDataStore by preferencesDataStore(name = "theme_prefs")

class ThemePrefs(private val context: Context) {

    val family: Flow<ThemeFamily> = context.themeDataStore.data.map { prefs ->
        prefs[FAMILY_KEY]?.let { ThemeFamily.valueOf(it) } ?: ThemeFamily.PAPER
    }

    val mode: Flow<ThemeMode> = context.themeDataStore.data.map { prefs ->
        prefs[MODE_KEY]?.let { ThemeMode.valueOf(it) } ?: ThemeMode.SYSTEM
    }

    suspend fun setFamily(family: ThemeFamily) {
        context.themeDataStore.edit { it[FAMILY_KEY] = family.name }
    }

    suspend fun setMode(mode: ThemeMode) {
        context.themeDataStore.edit { it[MODE_KEY] = mode.name }
    }

    companion object {
        private val FAMILY_KEY = stringPreferencesKey("theme_family")
        private val MODE_KEY = stringPreferencesKey("theme_mode")
    }
}
