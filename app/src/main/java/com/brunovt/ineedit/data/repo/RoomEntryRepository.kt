package com.brunovt.ineedit.data.repo

import com.brunovt.ineedit.data.local.EntryDao
import com.brunovt.ineedit.data.local.TagDao
import com.brunovt.ineedit.data.local.StatusDao
import com.brunovt.ineedit.di.AppScope
import com.brunovt.ineedit.domain.model.Column
import com.brunovt.ineedit.domain.model.Entry
import com.brunovt.ineedit.domain.model.Status
import com.brunovt.ineedit.domain.model.Tag
import com.brunovt.ineedit.domain.usecase.EntryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomEntryRepository @Inject constructor(
    private val dao: EntryDao,
    private val tagDao: TagDao,
    private val statusDao: StatusDao,
    @AppScope private val appScope: CoroutineScope,
) : EntryRepository {

    private val tagsMap = tagDao.observeAll()
        .map { list -> list.associate { it.id to Tag(it.id, it.name, it.sortOrder) } }
        .stateIn(appScope, SharingStarted.Eagerly, emptyMap())

    private val statusesMap = statusDao.observeAll()
        .map { list -> list.associate { it.id to Status(it.id, it.name, it.sortOrder) } }
        .stateIn(appScope, SharingStarted.Eagerly, emptyMap())

    override fun observeActive(column: Column): Flow<List<Entry>> =
        combine(
            dao.observeActive(column.name),
            tagsMap,
            statusesMap,
        ) { entities, tags, statuses ->
            entities.map { it.toDomain(tags, statuses) }
        }
            .distinctUntilChanged()
            .flowOn(Dispatchers.Default)

    override fun observeDone(): Flow<List<Entry>> =
        combine(
            dao.observeDone(),
            tagsMap,
            statusesMap,
        ) { entities, tags, statuses ->
            entities.map { it.toDomain(tags, statuses) }
        }
            .distinctUntilChanged()
            .flowOn(Dispatchers.Default)

    override suspend fun byId(id: String): Entry? = withContext(Dispatchers.IO) {
        val entity = dao.byId(id) ?: return@withContext null
        entity.toDomain(tagsMap.value, statusesMap.value)
    }

    override suspend fun upsert(entry: Entry) = withContext(Dispatchers.IO) {
        dao.upsert(entry.toEntity())
    }

    override suspend fun delete(id: String) = withContext(Dispatchers.IO) {
        dao.delete(id)
    }
}
