package com.brunovt.ineedit.data.repo

import com.brunovt.ineedit.domain.model.Tag
import com.brunovt.ineedit.domain.usecase.TagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeTagRepository : TagRepository {

    private val store = MutableStateFlow<List<Tag>>(emptyList())

    fun seed(vararg tags: Tag) {
        store.value = tags.toList()
    }

    override fun observeAll(): Flow<List<Tag>> = store

    override suspend fun all(): List<Tag> = store.value

    override suspend fun upsert(tag: Tag) {
        store.value = store.value.filter { it.id != tag.id } + tag
    }

    override suspend fun delete(id: String) {
        store.value = store.value.filter { it.id != id }
    }
}
