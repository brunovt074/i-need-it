package com.brunovt.ineedit.domain.usecase

import com.brunovt.ineedit.domain.model.Column
import com.brunovt.ineedit.domain.model.Entry
import kotlinx.coroutines.flow.Flow

class ObserveBoardUseCase(private val repository: EntryRepository) {
    operator fun invoke(column: Column): Flow<List<Entry>> =
        repository.observeActive(column)
}
