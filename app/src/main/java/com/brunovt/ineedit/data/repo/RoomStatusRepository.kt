package com.brunovt.ineedit.data.repo

import com.brunovt.ineedit.data.local.StatusDao
import com.brunovt.ineedit.data.local.StatusEntity
import com.brunovt.ineedit.domain.model.Status
import com.brunovt.ineedit.domain.usecase.StatusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomStatusRepository @Inject constructor(private val dao: StatusDao) : StatusRepository {

    override fun observeAll(): Flow<List<Status>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override suspend fun upsert(status: Status) = dao.upsert(status.toEntity())

    override suspend fun delete(id: String) = dao.delete(id)

    private fun StatusEntity.toDomain() = Status(id = id, name = name, sortOrder = sortOrder)
    private fun Status.toEntity() = StatusEntity(id = id, name = name, sortOrder = sortOrder)
}
