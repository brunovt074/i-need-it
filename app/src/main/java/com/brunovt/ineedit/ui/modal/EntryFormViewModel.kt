package com.brunovt.ineedit.ui.modal

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brunovt.ineedit.domain.model.Action
import com.brunovt.ineedit.domain.model.Column
import com.brunovt.ineedit.domain.model.Entry
import com.brunovt.ineedit.domain.model.Money
import com.brunovt.ineedit.domain.model.Status
import com.brunovt.ineedit.domain.model.Tag
import com.brunovt.ineedit.domain.model.TimeKey
import com.brunovt.ineedit.domain.usecase.EntryRepository
import com.brunovt.ineedit.domain.usecase.MarkDoneUseCase
import com.brunovt.ineedit.domain.usecase.MoveColumnUseCase
import com.brunovt.ineedit.domain.usecase.StatusRepository
import com.brunovt.ineedit.domain.usecase.TagRepository
import com.brunovt.ineedit.domain.usecase.UpsertEntryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import java.util.UUID
import javax.inject.Inject

data class EntryFormState(
    val name: String = "",
    val timeKey: TimeKey? = null,
    val specificDate: LocalDate? = null,
    val costAmount: String = "",
    val costCurrency: String = "ARS",
    val place: String = "",
    val selectedTags: PersistentSet<Tag> = persistentSetOf(),
    val status: Status? = null,
    val actions: PersistentList<Action> = persistentListOf(),
    val newActionText: String = "",
    val nameError: Boolean = false,
    val isSaving: Boolean = false,
    val savedSuccessfully: Boolean = false,
    val isExisting: Boolean = false,
    val column: Column = Column.NEED,
)

@HiltViewModel
class EntryFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val upsertEntry: UpsertEntryUseCase,
    private val markDone: MarkDoneUseCase,
    private val moveColumn: MoveColumnUseCase,
    private val repository: EntryRepository,
    tagRepository: TagRepository,
    statusRepository: StatusRepository,
) : ViewModel() {

    private val entryId: String? = savedStateHandle["entryId"] as? String
    private val initialColumn: Column = Column.valueOf((savedStateHandle["column"] ?: "NEED") as String)

    private val _state = MutableStateFlow(EntryFormState(column = initialColumn))
    val state: StateFlow<EntryFormState> = _state.asStateFlow()

    val availableTags: StateFlow<List<Tag>> = tagRepository.observeAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val availableStatuses: StateFlow<List<Status>> = statusRepository.observeAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        entryId?.let { id ->
            viewModelScope.launch {
                repository.byId(id)?.let { entry -> populateForm(entry) }
            }
        }
    }

    private fun populateForm(entry: Entry) {
        _state.update {
            it.copy(
                name = entry.name,
                timeKey = entry.timeKey,
                specificDate = entry.specificDate,
                costAmount = entry.cost?.amountMinor?.toString() ?: "",
                costCurrency = entry.cost?.currency ?: "ARS",
                place = entry.place ?: "",
                selectedTags = entry.tags.toPersistentSet(),
                status = entry.status,
                actions = entry.actions.toPersistentList(),
                isExisting = true,
                column = entry.column,
            )
        }
    }

    fun onNameChange(value: String) {
        _state.update { it.copy(name = value, nameError = false) }
    }

    fun onTimeKeyToggle(key: TimeKey) {
        _state.update {
            val newKey = if (it.timeKey == key) null else key
            it.copy(
                timeKey = newKey,
                specificDate = if (newKey != TimeKey.SPECIFIC_DATE) null else it.specificDate,
            )
        }
    }

    fun onSpecificDateChange(date: LocalDate?) {
        _state.update { it.copy(specificDate = date) }
    }

    fun onCostAmountChange(value: String) {
        _state.update { it.copy(costAmount = value) }
    }

    fun onCostCurrencyChange(value: String) {
        _state.update { it.copy(costCurrency = value) }
    }

    fun onPlaceChange(value: String) {
        _state.update { it.copy(place = value) }
    }

    fun onTagToggle(tag: Tag) {
        _state.update { s ->
            val tags = if (tag in s.selectedTags) s.selectedTags.remove(tag) else s.selectedTags.add(tag)
            s.copy(selectedTags = tags)
        }
    }

    fun onStatusToggle(status: Status) {
        _state.update { s ->
            s.copy(status = if (s.status?.id == status.id) null else status)
        }
    }

    fun onNewActionTextChange(text: String) {
        _state.update { it.copy(newActionText = text) }
    }

    fun addAction() {
        val text = _state.value.newActionText.trim()
        if (text.isBlank()) return
        val action = Action(id = UUID.randomUUID().toString(), text = text)
        _state.update { it.copy(actions = it.actions.add(action), newActionText = "") }
    }

    fun toggleActionChecked(actionId: String) {
        _state.update { s ->
            s.copy(actions = s.actions.map { a ->
                if (a.id == actionId) a.copy(checked = !a.checked) else a
            }.toPersistentList())
        }
    }

    fun removeAction(actionId: String) {
        _state.update { s ->
            s.copy(actions = s.actions.removeAll { it.id == actionId })
        }
    }

    fun save() {
        val s = _state.value
        if (s.name.isBlank()) {
            _state.update { it.copy(nameError = true) }
            return
        }
        val now = Clock.System.now()
        val entry = Entry(
            id = entryId ?: UUID.randomUUID().toString(),
            column = s.column,
            name = s.name.trim(),
            timeKey = s.timeKey,
            specificDate = s.specificDate,
            cost = s.costAmount.toLongOrNull()?.let { Money(it, s.costCurrency) },
            place = s.place.trim().ifBlank { null },
            tags = s.selectedTags.toList(),
            status = s.status,
            actions = s.actions,
            completedAt = null,
            createdAt = now,
            updatedAt = now,
        )
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }
            runCatching { upsertEntry(entry) }
                .onSuccess { _state.update { it.copy(isSaving = false, savedSuccessfully = true) } }
                .onFailure { _state.update { it.copy(isSaving = false) } }
        }
    }

    fun delete() {
        val id = entryId ?: return
        viewModelScope.launch {
            runCatching { repository.delete(id) }
                .onSuccess { _state.update { it.copy(savedSuccessfully = true) } }
        }
    }

    fun complete() {
        val id = entryId ?: return
        viewModelScope.launch {
            runCatching { markDone(id) }
                .onSuccess { _state.update { it.copy(savedSuccessfully = true) } }
        }
    }

    fun moveTo(targetColumn: Column) {
        val id = entryId ?: return
        viewModelScope.launch {
            runCatching { moveColumn(id, targetColumn) }
                .onSuccess { _state.update { it.copy(savedSuccessfully = true) } }
        }
    }
}
