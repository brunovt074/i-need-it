package com.brunovt.ineedit.data.repo

import com.brunovt.ineedit.domain.model.Column
import com.brunovt.ineedit.domain.model.Entry
import com.brunovt.ineedit.domain.usecase.EntryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.yield

class FakeEntryRepository : EntryRepository {

    private val store = MutableStateFlow<Map<String, Entry>>(emptyMap())

    fun seed(vararg entries: Entry) {
        store.value = store.value + entries.associateBy { it.id }
    }

    fun clear() {
        store.value = emptyMap()
    }

    override fun observeActive(column: Column): Flow<List<Entry>> =
        store.map { map ->
            map.values.filter { it.column == column && it.completedAt == null }
        }

    override fun observeDone(): Flow<List<Entry>> =
        store.map { map ->
            map.values.filter { it.completedAt != null }.sortedByDescending { it.completedAt }
        }

    override suspend fun byId(id: String): Entry? = store.value[id]

    override suspend fun upsert(entry: Entry) {
        yield()
        store.value = store.value + (entry.id to entry)
    }

    override suspend fun delete(id: String) {
        store.value = store.value - id
    }
}
