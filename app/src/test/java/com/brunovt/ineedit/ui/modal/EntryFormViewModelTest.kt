package com.brunovt.ineedit.ui.modal

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.brunovt.ineedit.data.repo.FakeEntryRepository
import com.brunovt.ineedit.data.repo.FakeStatusRepository
import com.brunovt.ineedit.data.repo.FakeTagRepository
import com.brunovt.ineedit.domain.model.Column
import com.brunovt.ineedit.domain.usecase.MarkDoneUseCase
import com.brunovt.ineedit.domain.usecase.MoveColumnUseCase
import com.brunovt.ineedit.domain.usecase.UpsertEntryUseCase
import com.brunovt.ineedit.util.EntryTestFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class EntryFormViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val entryRepository = FakeEntryRepository()
    private val tagRepository = FakeTagRepository()
    private val statusRepository = FakeStatusRepository()

    private fun buildViewModel(entryId: String? = null, column: Column = Column.NEED): EntryFormViewModel {
        val handle = SavedStateHandle(
            buildMap {
                if (entryId != null) put("entryId", entryId)
                put("column", column.name)
            }
        )
        return EntryFormViewModel(
            savedStateHandle = handle,
            upsertEntry = UpsertEntryUseCase(entryRepository),
            markDone = MarkDoneUseCase(entryRepository),
            moveColumn = MoveColumnUseCase(entryRepository),
            repository = entryRepository,
            tagRepository = tagRepository,
            statusRepository = statusRepository,
        )
    }

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        entryRepository.clear()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `save with valid name should set isSaving true then savedSuccessfully true`() = runTest {
        val viewModel = buildViewModel()
        viewModel.onNameChange("Toothpaste")

        viewModel.state.test {
            awaitItem() // current state with name set

            viewModel.save()

            val saving = awaitItem()
            assertTrue(saving.isSaving)
            assertFalse(saving.savedSuccessfully)

            val done = awaitItem()
            assertFalse(done.isSaving)
            assertTrue(done.savedSuccessfully)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `save with blank name should set nameError and not change isSaving`() = runTest {
        val viewModel = buildViewModel()

        viewModel.state.test {
            awaitItem()

            viewModel.save()

            val errorState = awaitItem()
            assertTrue(errorState.nameError)
            assertFalse(errorState.isSaving)
            assertFalse(errorState.savedSuccessfully)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `save with valid name should persist entry to repository`() = runTest {
        val viewModel = buildViewModel(column = Column.WANT)
        viewModel.onNameChange("New bike")

        viewModel.save()

        val saved = entryRepository.observeActive(Column.WANT)
        saved.test {
            val items = awaitItem()
            assertTrue(items.any { it.name == "New bike" && it.column == Column.WANT })
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `save existing entry should update it in repository`() = runTest {
        val existing = EntryTestFactory.active(id = "e1", name = "Old name", column = Column.NEED)
        entryRepository.seed(existing)
        val viewModel = buildViewModel(entryId = "e1")
        viewModel.onNameChange("New name")

        viewModel.save()

        val updated = entryRepository.byId("e1")
        assertTrue(updated?.name == "New name")
    }

    @Test
    fun `delete existing entry should set savedSuccessfully true`() = runTest {
        val existing = EntryTestFactory.active(id = "e1")
        entryRepository.seed(existing)
        val viewModel = buildViewModel(entryId = "e1")

        viewModel.state.test {
            awaitItem()

            viewModel.delete()

            val done = awaitItem()
            assertTrue(done.savedSuccessfully)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `complete existing entry should set savedSuccessfully true`() = runTest {
        val existing = EntryTestFactory.active(id = "e1")
        entryRepository.seed(existing)
        val viewModel = buildViewModel(entryId = "e1")

        viewModel.state.test {
            awaitItem()

            viewModel.complete()

            val done = awaitItem()
            assertTrue(done.savedSuccessfully)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
