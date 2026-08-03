package com.brunovt.ineedit.ui.modal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.brunovt.ineedit.R
import com.brunovt.ineedit.domain.model.Action
import com.brunovt.ineedit.domain.model.Column
import com.brunovt.ineedit.domain.model.Status
import com.brunovt.ineedit.domain.model.Tag
import com.brunovt.ineedit.domain.model.TimeKey
import com.brunovt.ineedit.ui.components.SelectionChip
import com.brunovt.ineedit.ui.theme.LocalAppTokens
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ItemForm(
    state: EntryFormState,
    availableTags: List<Tag>,
    availableStatuses: List<Status>,
    onNameChange: (String) -> Unit,
    onTimeKeyToggle: (TimeKey) -> Unit,
    onSpecificDateChange: (LocalDate?) -> Unit,
    onTagToggle: (Tag) -> Unit,
    onStatusToggle: (Status) -> Unit,
    onCostAmountChange: (String) -> Unit,
    onPlaceChange: (String) -> Unit,
    onNewActionTextChange: (String) -> Unit,
    onAddAction: () -> Unit,
    onToggleActionChecked: (String) -> Unit,
    onRemoveAction: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    onDelete: (() -> Unit)? = null,
    onComplete: (() -> Unit)? = null,
) {
    val tokens = LocalAppTokens.current
    val accentColor = when (state.column) {
        Column.NEED -> tokens.needSolid
        Column.WANT -> tokens.wantSolid
        Column.WISH -> tokens.wishSolid
    }

    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = state.specificDate
                ?.let { it.toEpochDays().toLong() * 86_400_000L }
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val millis = datePickerState.selectedDateMillis
                    if (millis != null) {
                        val local = Instant.fromEpochMilliseconds(millis)
                            .toLocalDateTime(TimeZone.UTC).date
                        onSpecificDateChange(local)
                    }
                    showDatePicker = false
                }) { Text(stringResource(R.string.action_done)) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(stringResource(R.string.form_cancel))
                }
            },
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        OutlinedTextField(
            value = state.name,
            onValueChange = onNameChange,
            label = { Text(text = stringResource(R.string.form_name_label)) },
            placeholder = { Text(text = stringResource(R.string.form_name_placeholder)) },
            isError = state.nameError,
            supportingText = if (state.nameError) {
                { Text(text = stringResource(R.string.form_required)) }
            } else null,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )

        Text(
            text = stringResource(R.string.form_time_label),
            style = MaterialTheme.typography.labelMedium,
            color = tokens.inkSoft,
        )
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            TimeKey.entries.forEach { key ->
                SelectionChip(
                    label = key.toLabel(),
                    selected = state.timeKey == key,
                    onClick = {
                        onTimeKeyToggle(key)
                        if (key == TimeKey.SPECIFIC_DATE && state.timeKey != key) {
                            showDatePicker = true
                        }
                    },
                    accentColor = accentColor,
                )
            }
        }

        if (state.timeKey == TimeKey.SPECIFIC_DATE) {
            OutlinedButton(
                onClick = { showDatePicker = true },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = state.specificDate?.toString()
                        ?: stringResource(R.string.time_pick_date),
                )
            }
        }

        OutlinedTextField(
            value = state.costAmount,
            onValueChange = onCostAmountChange,
            label = { Text(text = stringResource(R.string.form_cost_label)) },
            placeholder = { Text(text = stringResource(R.string.form_optional)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )

        OutlinedTextField(
            value = state.place,
            onValueChange = onPlaceChange,
            label = { Text(text = stringResource(R.string.form_place_label)) },
            placeholder = { Text(text = stringResource(R.string.form_place_placeholder)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )

        if (availableTags.isNotEmpty()) {
            Text(
                text = stringResource(R.string.form_tags_label),
                style = MaterialTheme.typography.labelMedium,
                color = tokens.inkSoft,
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                availableTags.forEach { tag ->
                    SelectionChip(
                        label = tag.name,
                        selected = tag in state.selectedTags,
                        onClick = { onTagToggle(tag) },
                        accentColor = accentColor,
                    )
                }
            }
        }

        if (availableStatuses.isNotEmpty()) {
            Text(
                text = stringResource(R.string.form_status_label),
                style = MaterialTheme.typography.labelMedium,
                color = tokens.inkSoft,
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                availableStatuses.forEach { status ->
                    SelectionChip(
                        label = status.name,
                        selected = state.status?.id == status.id,
                        onClick = { onStatusToggle(status) },
                        accentColor = accentColor,
                    )
                }
            }
        }

        Text(
            text = stringResource(R.string.form_actions_label),
            style = MaterialTheme.typography.labelMedium,
            color = tokens.inkSoft,
        )
        state.actions.forEach { action ->
            ActionRow(
                action = action,
                onToggle = { onToggleActionChecked(action.id) },
                onRemove = { onRemoveAction(action.id) },
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            OutlinedTextField(
                value = state.newActionText,
                onValueChange = onNewActionTextChange,
                placeholder = { Text(text = stringResource(R.string.form_action_placeholder)) },
                modifier = Modifier.weight(1f),
                singleLine = true,
            )
            IconButton(onClick = onAddAction) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.action_add),
                    tint = accentColor,
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        var cancelTapped by remember { mutableStateOf(false) }
        var saveTapped by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            OutlinedButton(
                onClick = {
                    if (!cancelTapped) {
                        cancelTapped = true
                        onCancel()
                    }
                },
                enabled = !cancelTapped && !state.isSaving,
                modifier = Modifier.weight(1f),
            ) {
                Text(text = stringResource(R.string.form_cancel))
            }
            Button(
                onClick = {
                    if (!saveTapped) {
                        saveTapped = true
                        onSave()
                    }
                },
                enabled = !saveTapped && !state.isSaving,
                modifier = Modifier.weight(1f),
            ) {
                Text(text = stringResource(R.string.form_save))
            }
        }

        if (state.isExisting) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                onComplete?.let {
                    TextButton(
                        onClick = it,
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(text = stringResource(R.string.form_complete))
                    }
                }
                onDelete?.let {
                    TextButton(
                        onClick = it,
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            text = stringResource(R.string.form_delete),
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun ActionRow(
    action: Action,
    onToggle: () -> Unit,
    onRemove: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Checkbox(checked = action.checked, onCheckedChange = { onToggle() })
        Text(
            text = action.text,
            style = MaterialTheme.typography.bodyMedium.let {
                if (action.checked) it.copy(textDecoration = TextDecoration.LineThrough) else it
            },
            modifier = Modifier.weight(1f),
        )
        IconButton(onClick = onRemove) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun TimeKey.toLabel(): String = when (this) {
    TimeKey.SPECIFIC_DATE -> stringResource(R.string.time_specific_date)
    TimeKey.TODAY -> stringResource(R.string.time_today)
    TimeKey.THIS_WEEK -> stringResource(R.string.time_this_week)
    TimeKey.TWO_WEEKS -> stringResource(R.string.time_two_weeks)
    TimeKey.THIS_MONTH -> stringResource(R.string.time_this_month)
    TimeKey.THREE_MONTHS -> stringResource(R.string.time_three_months)
    TimeKey.SIX_MONTHS -> stringResource(R.string.time_six_months)
    TimeKey.ONE_YEAR -> stringResource(R.string.time_one_year)
    TimeKey.MORE_THAN_YEAR -> stringResource(R.string.time_more_than_year)
}
