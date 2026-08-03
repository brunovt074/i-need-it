package com.brunovt.ineedit.ui.modal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brunovt.ineedit.ui.components.FastBottomSheet

@Composable
fun EntryFormSheet(
    onDismiss: () -> Unit,
    viewModel: EntryFormViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val availableTags by viewModel.availableTags.collectAsStateWithLifecycle()
    val availableStatuses by viewModel.availableStatuses.collectAsStateWithLifecycle()

    LaunchedEffect(state.savedSuccessfully) {
        if (state.savedSuccessfully) onDismiss()
    }

    FastBottomSheet(onDismiss = onDismiss) {
        ItemForm(
            state = state,
            availableTags = availableTags,
            availableStatuses = availableStatuses,
            onNameChange = viewModel::onNameChange,
            onTimeKeyToggle = viewModel::onTimeKeyToggle,
            onSpecificDateChange = viewModel::onSpecificDateChange,
            onTagToggle = viewModel::onTagToggle,
            onStatusToggle = viewModel::onStatusToggle,
            onCostAmountChange = viewModel::onCostAmountChange,
            onPlaceChange = viewModel::onPlaceChange,
            onNewActionTextChange = viewModel::onNewActionTextChange,
            onAddAction = viewModel::addAction,
            onToggleActionChecked = viewModel::toggleActionChecked,
            onRemoveAction = viewModel::removeAction,
            onSave = viewModel::save,
            onCancel = onDismiss,
            onDelete = if (state.isExisting) viewModel::delete else null,
            onComplete = if (state.isExisting) viewModel::complete else null,
        )
    }
}
