package com.brunovt.ineedit.domain.usecase

import kotlinx.datetime.Clock

class MarkDoneUseCase(private val repository: EntryRepository) {
    suspend operator fun invoke(entryId: String) {
        val entry = requireNotNull(repository.byId(entryId)) { "Entry not found: $entryId" }
        repository.upsert(entry.copy(completedAt = Clock.System.now()))
    }
}
