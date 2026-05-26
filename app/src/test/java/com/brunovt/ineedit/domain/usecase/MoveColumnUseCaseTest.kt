package com.brunovt.ineedit.domain.usecase

import com.brunovt.ineedit.data.repo.FakeEntryRepository
import com.brunovt.ineedit.domain.model.Column
import com.brunovt.ineedit.util.EntryTestFactory
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MoveColumnUseCaseTest {

    private val repository = FakeEntryRepository()
    private val useCase = MoveColumnUseCase(repository)

    @BeforeEach
    fun setUp() = repository.clear()

    @Test
    fun `should update entry column to target column`() = runTest {
        val entry = EntryTestFactory.active(id = "e1", column = Column.NEED)
        repository.seed(entry)

        useCase("e1", Column.WANT)

        val updated = repository.byId("e1")
        assertEquals(Column.WANT, updated?.column)
    }

    @Test
    fun `should update updatedAt timestamp when moving column`() = runTest {
        val entry = EntryTestFactory.active(id = "e1", column = Column.NEED)
        repository.seed(entry)
        val originalUpdatedAt = entry.updatedAt

        useCase("e1", Column.WISH)

        val updated = repository.byId("e1")
        assert(updated?.updatedAt?.epochSeconds!! >= originalUpdatedAt.epochSeconds) {
            "updatedAt should not decrease"
        }
    }

    @Test
    fun `should preserve all other fields when moving column`() = runTest {
        val entry = EntryTestFactory.active(id = "e1", name = "Guitar lessons", column = Column.WANT)
        repository.seed(entry)

        useCase("e1", Column.WISH)

        val updated = repository.byId("e1")
        assertEquals("Guitar lessons", updated?.name)
        assertEquals(entry.completedAt, updated?.completedAt)
        assertEquals(entry.createdAt, updated?.createdAt)
    }

    @Test
    fun `should throw when entry does not exist`() = runTest {
        assertThrows<IllegalArgumentException> { useCase("nonexistent-id", Column.NEED) }
    }
}
