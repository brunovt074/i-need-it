package com.brunovt.ineedit.util

import com.brunovt.ineedit.domain.model.Column
import com.brunovt.ineedit.domain.model.Entry
import com.brunovt.ineedit.domain.model.Tag
import com.brunovt.ineedit.domain.model.TimeKey
import kotlinx.datetime.Clock

object EntryTestFactory {

    fun active(
        id: String = "test-${System.nanoTime()}",
        column: Column = Column.NEED,
        name: String = "Test Entry",
        timeKey: TimeKey? = TimeKey.THIS_WEEK,
        tags: List<Tag> = emptyList(),
    ): Entry {
        val now = Clock.System.now()
        return Entry(
            id = id,
            column = column,
            name = name,
            timeKey = timeKey,
            specificDate = null,
            cost = null,
            place = null,
            tags = tags,
            status = null,
            actions = emptyList(),
            completedAt = null,
            createdAt = now,
            updatedAt = now,
        )
    }

    fun done(
        id: String = "done-${System.nanoTime()}",
        name: String = "Completed Entry",
    ): Entry = active(id = id, name = name).copy(completedAt = Clock.System.now())
}
