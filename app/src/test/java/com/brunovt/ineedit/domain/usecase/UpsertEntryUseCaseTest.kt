package com.brunovt.ineedit.domain.usecase

import com.brunovt.ineedit.data.repo.FakeEntryRepository
import com.brunovt.ineedit.domain.model.TimeKey
import com.brunovt.ineedit.util.EntryTestFactory
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UpsertEntryUseCaseTest {

    private val repository = FakeEntryRepository()
    private val useCase = UpsertEntryUseCase(repository)

    @BeforeEach
    fun setUp() = repository.clear()

    @Test
    fun `should save entry with non-blank name`() = runTest {
        val entry = EntryTestFactory.active(name = "Toothpaste")

        useCase(entry)

        val saved = repository.byId(entry.id)
        assertNotNull(saved)
        assertEquals("Toothpaste", saved?.name)
    }

    @Test
    fun `should throw when name is blank`() = runTest {
        val entry = EntryTestFactory.active(name = "   ")

        assertThrows<IllegalArgumentException> { useCase(entry) }
    }

    @Test
    fun `should throw when SPECIFIC_DATE selected but no date provided`() = runTest {
        val entry = EntryTestFactory.active(timeKey = TimeKey.SPECIFIC_DATE)
            .copy(specificDate = null)

        assertThrows<IllegalArgumentException> { useCase(entry) }
    }

    @Test
    fun `should throw when specificDate provided but timeKey is not SPECIFIC_DATE`() = runTest {
        val entry = EntryTestFactory.active(timeKey = TimeKey.THIS_WEEK)
            .copy(specificDate = LocalDate(2025, 12, 25))

        assertThrows<IllegalArgumentException> { useCase(entry) }
    }

    @Test
    fun `should save entry with SPECIFIC_DATE when specificDate is also provided`() = runTest {
        val entry = EntryTestFactory.active(timeKey = TimeKey.SPECIFIC_DATE)
            .copy(specificDate = LocalDate(2025, 12, 25))

        useCase(entry)

        val saved = repository.byId(entry.id)
        assertNotNull(saved)
        assertEquals(TimeKey.SPECIFIC_DATE, saved?.timeKey)
        assertEquals(LocalDate(2025, 12, 25), saved?.specificDate)
    }
}
