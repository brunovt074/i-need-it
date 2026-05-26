package com.brunovt.ineedit.domain.usecase

import com.brunovt.ineedit.domain.model.Tag
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    fun observeAll(): Flow<List<Tag>>
    suspend fun all(): List<Tag>
    suspend fun upsert(tag: Tag)
    suspend fun delete(id: String)
}
