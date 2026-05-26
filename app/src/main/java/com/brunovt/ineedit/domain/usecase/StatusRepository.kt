package com.brunovt.ineedit.domain.usecase

import com.brunovt.ineedit.domain.model.Status
import kotlinx.coroutines.flow.Flow

interface StatusRepository {
    fun observeAll(): Flow<List<Status>>
    suspend fun upsert(status: Status)
    suspend fun delete(id: String)
}
