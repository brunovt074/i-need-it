package com.brunovt.ineedit.data.prefs

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.localeDataStore by preferencesDataStore(name = "locale_prefs")

class LocalePrefs(private val context: Context) {

    val locale: Flow<String> = context.localeDataStore.data.map { prefs ->
        prefs[LOCALE_KEY] ?: "en"
    }

    suspend fun setLocale(tag: String) {
        context.localeDataStore.edit { it[LOCALE_KEY] = tag }
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(tag))
    }

    companion object {
        private val LOCALE_KEY = stringPreferencesKey("locale")
    }
}
