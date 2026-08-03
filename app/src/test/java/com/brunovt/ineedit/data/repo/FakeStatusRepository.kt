package com.brunovt.ineedit.data.repo

import com.brunovt.ineedit.domain.model.Status
import com.brunovt.ineedit.domain.usecase.StatusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeStatusRepository : StatusRepository {

    private val store = MutableStateFlow<List<Status>>(emptyList())

    fun seed(vararg statuses: Status) {
        store.value = statuses.toList()
    }

    override fun observeAll(): Flow<List<Status>> = store

    override suspend fun upsert(status: Status) {
        store.value = store.value.filter { it.id != status.id } + status
    }

    override suspend fun delete(id: String) {
        store.value = store.value.filter { it.id != id }
    }
}
