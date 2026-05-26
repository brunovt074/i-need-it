package com.brunovt.ineedit.ui.modal

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryFormSheet(
    onDismiss: () -> Unit,
    viewModel: EntryFormViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val availableTags by viewModel.availableTags.collectAsStateWithLifecycle()
    val availableStatuses by viewModel.availableStatuses.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(state.savedSuccessfully) {
        if (state.savedSuccessfully) onDismiss()
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
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
            onSave = { viewModel.save(onDismiss) },
            onCancel = onDismiss,
            onDelete = if (state.isExisting) viewModel::delete else null,
            onComplete = if (state.isExisting) viewModel::complete else null,
        )
    }
}
