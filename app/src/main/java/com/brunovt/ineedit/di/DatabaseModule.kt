package com.brunovt.ineedit.di

import android.content.Context
import androidx.room.Room
import com.brunovt.ineedit.data.local.AppDatabase
import com.brunovt.ineedit.data.local.EntryDao
import com.brunovt.ineedit.data.local.MIGRATION_1_2
import com.brunovt.ineedit.data.local.StatusDao
import com.brunovt.ineedit.data.local.TagDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "ineedit.db")
            .addMigrations(MIGRATION_1_2)
            .build()

    @Provides
    fun provideEntryDao(db: AppDatabase): EntryDao = db.entryDao()

    @Provides
    fun provideTagDao(db: AppDatabase): TagDao = db.tagDao()

    @Provides
    fun provideStatusDao(db: AppDatabase): StatusDao = db.statusDao()
}
