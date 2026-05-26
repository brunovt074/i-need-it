package com.brunovt.ineedit.domain.usecase

import com.brunovt.ineedit.domain.model.Column
import com.brunovt.ineedit.domain.model.Entry
import kotlinx.coroutines.flow.Flow

interface EntryRepository {
    fun observeActive(column: Column): Flow<List<Entry>>
    fun observeDone(): Flow<List<Entry>>
    suspend fun byId(id: String): Entry?
    suspend fun upsert(entry: Entry)
    suspend fun delete(id: String)
}
