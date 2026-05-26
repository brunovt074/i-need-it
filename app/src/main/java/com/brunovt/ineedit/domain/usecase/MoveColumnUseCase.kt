package com.brunovt.ineedit.domain.usecase

import com.brunovt.ineedit.domain.model.Column
import kotlinx.datetime.Clock

class MoveColumnUseCase(private val repository: EntryRepository) {
    suspend operator fun invoke(entryId: String, targetColumn: Column) {
        val entry = requireNotNull(repository.byId(entryId)) { "Entry not found: $entryId" }
        repository.upsert(
            entry.copy(
                column = targetColumn,
                updatedAt = Clock.System.now(),
            )
        )
    }
}
