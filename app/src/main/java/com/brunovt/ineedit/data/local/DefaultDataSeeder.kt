package com.brunovt.ineedit.data.local

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultDataSeeder @Inject constructor(
    private val tagDao: TagDao,
    private val statusDao: StatusDao,
) {

    suspend fun seedIfNeeded() {
        if (tagDao.all().isEmpty()) seedTags()
        if (statusDao.all().isEmpty()) seedStatuses()
    }

    private suspend fun seedTags() {
        listOf(
            TagEntity(id = "HEALTH", name = "Health", sortOrder = 0),
            TagEntity(id = "HOME", name = "Home", sortOrder = 1),
            TagEntity(id = "HOBBY", name = "Hobby", sortOrder = 2),
            TagEntity(id = "WORK", name = "Work", sortOrder = 3),
            TagEntity(id = "FAMILY", name = "Family", sortOrder = 4),
            TagEntity(id = "TRAVEL", name = "Travel", sortOrder = 5),
            TagEntity(id = "MONEY", name = "Finance", sortOrder = 6),
            TagEntity(id = "STUDY", name = "Study", sortOrder = 7),
            TagEntity(id = "FITNESS", name = "Fitness", sortOrder = 8),
        ).forEach { tagDao.upsert(it) }
    }

    private suspend fun seedStatuses() {
        listOf(
            StatusEntity(id = "TODO", name = "To do", sortOrder = 0),
            StatusEntity(id = "IN_PROGRESS", name = "In progress", sortOrder = 1),
            StatusEntity(id = "DONE", name = "Done", sortOrder = 2),
            StatusEntity(id = "BLOCKED", name = "Blocked", sortOrder = 3),
        ).forEach { statusDao.upsert(it) }
    }
}
