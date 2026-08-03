package com.brunovt.ineedit.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brunovt.ineedit.data.prefs.LayoutPrefs
import com.brunovt.ineedit.data.prefs.LayoutStyle
import com.brunovt.ineedit.domain.model.Column
import com.brunovt.ineedit.domain.model.Entry
import com.brunovt.ineedit.domain.usecase.MarkDoneUseCase
import com.brunovt.ineedit.domain.usecase.MoveColumnUseCase
import com.brunovt.ineedit.domain.usecase.ObserveBoardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val observeBoard: ObserveBoardUseCase,
    private val markDone: MarkDoneUseCase,
    private val moveColumn: MoveColumnUseCase,
    private val layoutPrefs: LayoutPrefs,
) : ViewModel() {

    val needItems: StateFlow<List<Entry>> = observeBoard(Column.NEED)
        .distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val wantItems: StateFlow<List<Entry>> = observeBoard(Column.WANT)
        .distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val wishItems: StateFlow<List<Entry>> = observeBoard(Column.WISH)
        .distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val layoutStyle: StateFlow<LayoutStyle> = layoutPrefs.layout
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), LayoutStyle.ACCORDION)

    private val _activeColumn = MutableStateFlow(Column.NEED)
    val activeColumn: StateFlow<Column> = _activeColumn.asStateFlow()

    fun setActiveColumn(column: Column) {
        _activeColumn.value = column
    }

    fun markDone(entryId: String) {
        viewModelScope.launch {
            runCatching { markDone.invoke(entryId) }
        }
    }

    fun moveEntry(entryId: String, targetColumn: Column) {
        viewModelScope.launch {
            runCatching { moveColumn(entryId, targetColumn) }
        }
    }

    fun toggleLayout() {
        viewModelScope.launch {
            val current = layoutStyle.value
            layoutPrefs.setLayout(
                if (current == LayoutStyle.ACCORDION) LayoutStyle.FAN else LayoutStyle.ACCORDION
            )
        }
    }
}
