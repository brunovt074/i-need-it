package com.brunovt.ineedit.data.prefs

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

enum class LayoutStyle { ACCORDION, FAN }

private val Context.layoutDataStore by preferencesDataStore(name = "layout_prefs")

class LayoutPrefs(private val context: Context) {

    val layout: Flow<LayoutStyle> = context.layoutDataStore.data.map { prefs ->
        prefs[LAYOUT_KEY]?.let { LayoutStyle.valueOf(it) } ?: LayoutStyle.ACCORDION
    }

    suspend fun setLayout(style: LayoutStyle) {
        context.layoutDataStore.edit { it[LAYOUT_KEY] = style.name }
    }

    companion object {
        private val LAYOUT_KEY = stringPreferencesKey("layout_style")
    }
}
