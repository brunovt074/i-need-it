package com.brunovt.ineedit.data.repo

import com.brunovt.ineedit.data.local.TagDao
import com.brunovt.ineedit.data.local.TagEntity
import com.brunovt.ineedit.domain.model.Tag
import com.brunovt.ineedit.domain.usecase.TagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomTagRepository @Inject constructor(private val dao: TagDao) : TagRepository {

    override fun observeAll(): Flow<List<Tag>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override suspend fun all(): List<Tag> = dao.all().map { it.toDomain() }

    override suspend fun upsert(tag: Tag) = dao.upsert(tag.toEntity())

    override suspend fun delete(id: String) = dao.delete(id)

    private fun TagEntity.toDomain() = Tag(id = id, name = name, sortOrder = sortOrder)
    private fun Tag.toEntity() = TagEntity(id = id, name = name, sortOrder = sortOrder)
}
