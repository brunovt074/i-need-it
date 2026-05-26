package com.brunovt.ineedit.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.brunovt.ineedit.domain.model.Column
import com.brunovt.ineedit.domain.model.Entry
import com.brunovt.ineedit.domain.model.Tag
import com.brunovt.ineedit.domain.model.TimeKey
import com.brunovt.ineedit.domain.usecase.EntryRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

private val Context.seedDataStore by preferencesDataStore(name = "debug_seed")

private fun tag(id: String, name: String) = Tag(id = id, name = name)
private val HEALTH = tag("HEALTH", "Health")
private val MONEY = tag("MONEY", "Finance")
private val HOME = tag("HOME", "Home")
private val FITNESS = tag("FITNESS", "Fitness")
private val HOBBY = tag("HOBBY", "Hobby")
private val STUDY = tag("STUDY", "Study")
private val TRAVEL = tag("TRAVEL", "Travel")
private val FAMILY = tag("FAMILY", "Family")

@Singleton
class SeedData @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val repository: EntryRepository,
) {

    private val seedDoneKey = booleanPreferencesKey("seed_done")

    suspend fun seedIfNeeded() {
        val prefs = appContext.seedDataStore.data.first()
        if (prefs[seedDoneKey] == true) return

        val now = Clock.System.now()

        val entries = listOf(
            anEntry(column = Column.NEED, name = "Buy toothpaste", timeKey = TimeKey.TODAY, place = "Pharmacy", tags = listOf(HEALTH), now = now),
            anEntry(column = Column.NEED, name = "Pay electricity bill", timeKey = TimeKey.THIS_WEEK, tags = listOf(MONEY, HOME), now = now),
            anEntry(column = Column.NEED, name = "Doctor appointment", timeKey = TimeKey.TWO_WEEKS, place = "Health center", tags = listOf(HEALTH), now = now),
            anEntry(column = Column.WANT, name = "New running shoes", timeKey = TimeKey.THIS_MONTH, place = "Sports store", tags = listOf(FITNESS), now = now),
            anEntry(column = Column.WANT, name = "Learn guitar", timeKey = TimeKey.THREE_MONTHS, tags = listOf(HOBBY, STUDY), now = now),
            anEntry(column = Column.WANT, name = "Bedroom repaint", timeKey = TimeKey.SIX_MONTHS, tags = listOf(HOME), now = now),
            anEntry(column = Column.WISH, name = "Travel to Japan", timeKey = TimeKey.ONE_YEAR, tags = listOf(TRAVEL, FAMILY), now = now),
            anEntry(column = Column.WISH, name = "Learn to cook sushi", timeKey = TimeKey.MORE_THAN_YEAR, tags = listOf(HOBBY), now = now),
            anEntry(column = Column.WISH, name = "Buy a house", timeKey = TimeKey.MORE_THAN_YEAR, tags = listOf(HOME, MONEY), now = now),
        )

        entries.forEach { repository.upsert(it) }
        appContext.seedDataStore.edit { it[seedDoneKey] = true }
    }

    private fun anEntry(
        column: Column,
        name: String,
        timeKey: TimeKey,
        place: String? = null,
        tags: List<Tag> = emptyList(),
        now: Instant,
    ) = Entry(
        id = UUID.randomUUID().toString(),
        column = column,
        name = name,
        timeKey = timeKey,
        specificDate = null,
        cost = null,
        place = place,
        tags = tags,
        status = null,
        actions = emptyList(),
        completedAt = null,
        createdAt = now,
        updatedAt = now,
    )
}
