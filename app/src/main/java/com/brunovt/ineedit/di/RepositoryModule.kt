package com.brunovt.ineedit.di

import com.brunovt.ineedit.data.repo.RoomEntryRepository
import com.brunovt.ineedit.data.repo.RoomStatusRepository
import com.brunovt.ineedit.data.repo.RoomTagRepository
import com.brunovt.ineedit.domain.usecase.EntryRepository
import com.brunovt.ineedit.domain.usecase.StatusRepository
import com.brunovt.ineedit.domain.usecase.TagRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindEntryRepository(impl: RoomEntryRepository): EntryRepository

    @Binds
    @Singleton
    abstract fun bindTagRepository(impl: RoomTagRepository): TagRepository

    @Binds
    @Singleton
    abstract fun bindStatusRepository(impl: RoomStatusRepository): StatusRepository
}
