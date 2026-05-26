package com.brunovt.ineedit.domain.usecase

import com.brunovt.ineedit.domain.model.Entry
import com.brunovt.ineedit.domain.model.TimeKey

class UpsertEntryUseCase(private val repository: EntryRepository) {
    suspend operator fun invoke(entry: Entry) {
        require(entry.name.isNotBlank()) { "Entry name must not be blank" }
        require((entry.timeKey == TimeKey.SPECIFIC_DATE) == (entry.specificDate != null)) {
            "specificDate must be set if and only if timeKey == SPECIFIC_DATE"
        }
        repository.upsert(entry)
    }
}
