package com.brunovt.ineedit.di

import com.brunovt.ineedit.domain.usecase.EntryRepository
import com.brunovt.ineedit.domain.usecase.MarkDoneUseCase
import com.brunovt.ineedit.domain.usecase.MoveColumnUseCase
import com.brunovt.ineedit.domain.usecase.ObserveBoardUseCase
import com.brunovt.ineedit.domain.usecase.UpsertEntryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideObserveBoardUseCase(repo: EntryRepository): ObserveBoardUseCase =
        ObserveBoardUseCase(repo)

    @Provides
    fun provideUpsertEntryUseCase(repo: EntryRepository): UpsertEntryUseCase =
        UpsertEntryUseCase(repo)

    @Provides
    fun provideMarkDoneUseCase(repo: EntryRepository): MarkDoneUseCase =
        MarkDoneUseCase(repo)

    @Provides
    fun provideMoveColumnUseCase(repo: EntryRepository): MoveColumnUseCase =
        MoveColumnUseCase(repo)
}
