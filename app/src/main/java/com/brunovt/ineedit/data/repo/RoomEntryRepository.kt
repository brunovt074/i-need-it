package com.brunovt.ineedit.data.repo

import com.brunovt.ineedit.data.local.EntryDao
import com.brunovt.ineedit.data.local.TagDao
import com.brunovt.ineedit.data.local.StatusDao
import com.brunovt.ineedit.domain.model.Column
import com.brunovt.ineedit.domain.model.Entry
import com.brunovt.ineedit.domain.model.Status
import com.brunovt.ineedit.domain.model.Tag
import com.brunovt.ineedit.domain.usecase.EntryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomEntryRepository @Inject constructor(
    private val dao: EntryDao,
    private val tagDao: TagDao,
    private val statusDao: StatusDao,
) : EntryRepository {

    override fun observeActive(column: Column): Flow<List<Entry>> =
        combine(
            dao.observeActive(column.name),
            tagDao.observeAll(),
            statusDao.observeAll(),
        ) { entities, tags, statuses ->
            val tagMap = tags.associate { it.id to Tag(it.id, it.name, it.sortOrder) }
            val statusMap = statuses.associate { it.id to Status(it.id, it.name, it.sortOrder) }
            entities.map { it.toDomain(tagMap, statusMap) }
        }

    override fun observeDone(): Flow<List<Entry>> =
        combine(
            dao.observeDone(),
            tagDao.observeAll(),
            statusDao.observeAll(),
        ) { entities, tags, statuses ->
            val tagMap = tags.associate { it.id to Tag(it.id, it.name, it.sortOrder) }
            val statusMap = statuses.associate { it.id to Status(it.id, it.name, it.sortOrder) }
            entities.map { it.toDomain(tagMap, statusMap) }
        }

    override suspend fun byId(id: String): Entry? {
        val entity = dao.byId(id) ?: return null
        val tagMap = tagDao.all().associate { it.id to Tag(it.id, it.name, it.sortOrder) }
        val statusMap = statusDao.all().associate { it.id to Status(it.id, it.name, it.sortOrder) }
        return entity.toDomain(tagMap, statusMap)
    }

    override suspend fun upsert(entry: Entry) = dao.upsert(entry.toEntity())

    override suspend fun delete(id: String) = dao.delete(id)
}
