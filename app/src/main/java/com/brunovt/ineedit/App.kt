package com.brunovt.ineedit

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.brunovt.ineedit.data.local.DefaultDataSeeder
import com.brunovt.ineedit.data.prefs.LocalePrefs
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltAndroidApp
open class App : Application() {

    @Inject
    lateinit var localePrefs: LocalePrefs

    @Inject
    lateinit var defaultDataSeeder: DefaultDataSeeder

    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        runBlocking {
            val tag = localePrefs.locale.first()
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(tag))
        }
        appScope.launch { defaultDataSeeder.seedIfNeeded() }
        onAppCreate()
    }

    open fun onAppCreate() {}
}
