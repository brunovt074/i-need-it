package com.brunovt.ineedit.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EntryDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: EntryDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java,
        ).allowMainThreadQueries().build()
        dao = db.entryDao()
    }

    @After
    fun tearDown() = db.close()

    @Test
    fun `upsert and observe active entries for column`() = runTest {
        val entity = anEntity(id = "1", column = "NEED", name = "Toothpaste")
        dao.upsert(entity)

        val results = dao.observeActive("NEED").first()

        assertEquals(1, results.size)
        assertEquals("Toothpaste", results[0].name)
    }

    @Test
    fun `completed entries do not appear in active list`() = runTest {
        dao.upsert(anEntity(id = "1", column = "NEED", completedAt = 9999L))

        val active = dao.observeActive("NEED").first()

        assertTrue(active.isEmpty())
    }

    @Test
    fun `completed entries appear in done list`() = runTest {
        dao.upsert(anEntity(id = "1", column = "NEED", name = "Done item", completedAt = 9999L))

        val done = dao.observeDone().first()

        assertEquals(1, done.size)
        assertEquals("Done item", done[0].name)
    }

    @Test
    fun `active entries do not appear in done list`() = runTest {
        dao.upsert(anEntity(id = "1", column = "NEED", completedAt = null))

        val done = dao.observeDone().first()

        assertTrue(done.isEmpty())
    }

    @Test
    fun `byId returns null when entry does not exist`() = runTest {
        val result = dao.byId("nonexistent")

        assertNull(result)
    }

    @Test
    fun `byId returns entry when it exists`() = runTest {
        val entity = anEntity(id = "1", column = "NEED", name = "Milk")
        dao.upsert(entity)

        val result = dao.byId("1")

        assertEquals("Milk", result?.name)
    }

    @Test
    fun `delete removes entry from active list`() = runTest {
        dao.upsert(anEntity(id = "1", column = "NEED"))

        dao.delete("1")

        val active = dao.observeActive("NEED").first()
        assertTrue(active.isEmpty())
    }

    @Test
    fun `upsert updates existing entry`() = runTest {
        dao.upsert(anEntity(id = "1", column = "NEED", name = "Old name"))
        dao.upsert(anEntity(id = "1", column = "NEED", name = "New name"))

        val result = dao.byId("1")

        assertEquals("New name", result?.name)
    }

    @Test
    fun `entries from different columns are independent`() = runTest {
        dao.upsert(anEntity(id = "1", column = "NEED", name = "Need item"))
        dao.upsert(anEntity(id = "2", column = "WANT", name = "Want item"))

        val needItems = dao.observeActive("NEED").first()
        val wantItems = dao.observeActive("WANT").first()

        assertEquals(1, needItems.size)
        assertEquals("Need item", needItems[0].name)
        assertEquals(1, wantItems.size)
        assertEquals("Want item", wantItems[0].name)
    }

    private fun anEntity(
        id: String,
        column: String,
        name: String = "Test",
        completedAt: Long? = null,
    ) = EntryEntity(
        id = id,
        column = column,
        name = name,
        timeKey = null,
        specificDate = null,
        costAmountMinor = null,
        costCurrency = null,
        place = null,
        tags = "[]",
        completedAt = completedAt,
        createdAt = 1000L,
        updatedAt = 1000L,
    )
}
