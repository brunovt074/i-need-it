package com.brunovt.ineedit.di

import android.content.Context
import com.brunovt.ineedit.data.prefs.LayoutPrefs
import com.brunovt.ineedit.data.prefs.LocalePrefs
import com.brunovt.ineedit.data.prefs.ThemePrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PrefsModule {

    @Provides
    @Singleton
    fun provideThemePrefs(@ApplicationContext context: Context): ThemePrefs =
        ThemePrefs(context)

    @Provides
    @Singleton
    fun provideLayoutPrefs(@ApplicationContext context: Context): LayoutPrefs =
        LayoutPrefs(context)

    @Provides
    @Singleton
    fun provideLocalePrefs(@ApplicationContext context: Context): LocalePrefs =
        LocalePrefs(context)
}
