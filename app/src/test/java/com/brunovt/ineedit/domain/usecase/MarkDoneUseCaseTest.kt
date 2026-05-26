package com.brunovt.ineedit.domain.usecase

import com.brunovt.ineedit.data.repo.FakeEntryRepository
import com.brunovt.ineedit.util.EntryTestFactory
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MarkDoneUseCaseTest {

    private val repository = FakeEntryRepository()
    private val useCase = MarkDoneUseCase(repository)

    @BeforeEach
    fun setUp() = repository.clear()

    @Test
    fun `should set completedAt for existing active entry`() = runTest {
        val entry = EntryTestFactory.active(id = "e1")
        repository.seed(entry)

        useCase("e1")

        val updated = repository.byId("e1")
        assertNotNull(updated?.completedAt)
    }

    @Test
    fun `should keep original fields intact after marking done`() = runTest {
        val entry = EntryTestFactory.active(id = "e1", name = "Buy milk")
        repository.seed(entry)

        useCase("e1")

        val updated = repository.byId("e1")
        assertNotNull(updated)
        assert(updated?.name == "Buy milk") { "Name should not change" }
        assert(updated?.column == entry.column) { "Column should not change" }
    }

    @Test
    fun `should throw when entry does not exist`() = runTest {
        assertThrows<IllegalArgumentException> { useCase("nonexistent-id") }
    }

    @Test
    fun `completedAt should be null for active entry before marking done`() = runTest {
        val entry = EntryTestFactory.active(id = "e1")
        repository.seed(entry)

        val before = repository.byId("e1")
        assertNull(before?.completedAt)
    }
}
