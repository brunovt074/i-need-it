package com.brunovt.ineedit.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.brunovt.ineedit.R
import com.brunovt.ineedit.domain.model.Entry
import com.brunovt.ineedit.domain.model.TimeKey
import com.brunovt.ineedit.ui.dashboard.LocalDragDrop
import com.brunovt.ineedit.ui.theme.LocalAppTokens

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ItemCard(
    entry: Entry,
    borderColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    initialExpanded: Boolean = false,
    onMoveToColumn: ((String, com.brunovt.ineedit.domain.model.Column) -> Unit)? = null,
) {
    val tokens = LocalAppTokens.current
    val dnd = LocalDragDrop.current
    var expanded by rememberSaveable(entry.id) { mutableStateOf(initialExpanded) }
    var cardWindowOffset by remember { mutableStateOf(Offset.Zero) }

    val hasBadges = entry.timeKey != null || entry.cost != null ||
            entry.place != null || entry.tags.isNotEmpty() ||
            entry.status != null || entry.actions.isNotEmpty()

    val isDraggingThis = dnd?.draggingEntry?.id == entry.id
    val dragModifier = if (dnd != null) {
        Modifier
            .onGloballyPositioned { coords ->
                val pos = coords.positionInWindow()
                cardWindowOffset = Offset(pos.x, pos.y)
            }
            .pointerInput(entry.id) {
                detectDragGesturesAfterLongPress(
                    onDragStart = { localOffset ->
                        dnd.startDrag(entry, cardWindowOffset + localOffset)
                    },
                    onDrag = { change, delta ->
                        change.consume()
                        dnd.onDragDelta(delta)
                    },
                    onDragEnd = {
                        val result = dnd.endDrag()
                        result?.let { (draggedEntry, col) ->
                            if (draggedEntry.column != col) {
                                onMoveToColumn?.invoke(draggedEntry.id, col)
                            }
                        }
                    },
                    onDragCancel = { dnd.cancelDrag() },
                )
            }
    } else Modifier

    Card(
        modifier = modifier
            .fillMaxWidth()
            .sizeIn(minHeight = 48.dp)
            .then(dragModifier)
            .alpha(if (isDraggingThis) 0.4f else 1f)
            .clickable(onClick = onClick),
        border = BorderStroke(
            width = if (isDraggingThis) 2.dp else 1.dp,
            color = borderColor,
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = entry.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = tokens.inkDefault,
                    modifier = Modifier.weight(1f),
                )
                if (hasBadges) {
                    IconButton(
                        onClick = { expanded = !expanded },
                        modifier = Modifier.size(32.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreHoriz,
                            contentDescription = null,
                            tint = tokens.inkFaded,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                }
            }

            if (expanded && hasBadges) {
                Spacer(modifier = Modifier.height(4.dp))

                if (entry.timeKey != null || entry.cost != null ||
                    entry.place != null || entry.status != null
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        entry.timeKey?.let { tk ->
                            SuggestionChip(
                                onClick = onClick,
                                label = {
                                    Text(
                                        text = tk.toLabel(),
                                        style = MaterialTheme.typography.labelSmall,
                                    )
                                },
                                colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = borderColor.copy(alpha = 0.1f),
                                ),
                            )
                        }
                        entry.status?.let { st ->
                            SuggestionChip(
                                onClick = onClick,
                                label = {
                                    Text(
                                        text = st.name,
                                        style = MaterialTheme.typography.labelSmall,
                                    )
                                },
                            )
                        }
                        entry.cost?.let { money ->
                            Text(
                                text = "${money.currency} ${money.amountMinor / 100}.${(money.amountMinor % 100).toString().padStart(2, '0')}",
                                style = MaterialTheme.typography.labelSmall,
                                color = tokens.inkSoft,
                            )
                        }
                        entry.place?.let { place ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Place,
                                    contentDescription = null,
                                    tint = tokens.inkFaded,
                                    modifier = Modifier.size(14.dp),
                                )
                                Text(
                                    text = place,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = tokens.inkSoft,
                                )
                            }
                        }
                    }
                }

                if (entry.tags.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        entry.tags.forEach { tag ->
                            SuggestionChip(
                                onClick = onClick,
                                label = {
                                    Text(
                                        text = tag.name,
                                        style = MaterialTheme.typography.labelSmall,
                                    )
                                },
                            )
                        }
                    }
                }

                if (entry.actions.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.form_actions_label),
                        style = MaterialTheme.typography.labelSmall,
                        color = tokens.inkFaded,
                    )
                    entry.actions.forEach { action ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Checkbox(
                                checked = action.checked,
                                onCheckedChange = null,
                                modifier = Modifier.size(20.dp),
                            )
                            Text(
                                text = action.text,
                                style = MaterialTheme.typography.bodySmall.let {
                                    if (action.checked)
                                        it.copy(textDecoration = TextDecoration.LineThrough)
                                    else it
                                },
                                color = if (action.checked) tokens.inkFaded else tokens.inkDefault,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 4.dp)
                                    .alpha(if (action.checked) 0.6f else 1f),
                            )
                        }
                    }
                }
            }
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
