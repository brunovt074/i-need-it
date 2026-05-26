package com.brunovt.ineedit.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brunovt.ineedit.domain.model.Entry
import com.brunovt.ineedit.domain.usecase.EntryRepository
import com.brunovt.ineedit.domain.usecase.UpsertEntryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class DoneViewModel @Inject constructor(
    private val repository: EntryRepository,
    private val upsertEntry: UpsertEntryUseCase,
) : ViewModel() {

    private val _items = MutableStateFlow<List<Entry>>(emptyList())
    val items: StateFlow<List<Entry>> = _items.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeDone().collect { _items.value = it }
        }
    }

    fun restore(entry: Entry) {
        viewModelScope.launch {
            runCatching {
                upsertEntry(
                    entry.copy(
                        completedAt = null,
                        updatedAt = Clock.System.now(),
                    )
                )
            }
        }
    }
}
