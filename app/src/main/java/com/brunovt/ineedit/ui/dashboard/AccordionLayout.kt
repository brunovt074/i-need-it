package com.brunovt.ineedit.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.brunovt.ineedit.R
import com.brunovt.ineedit.domain.model.Column
import com.brunovt.ineedit.domain.model.Entry
import com.brunovt.ineedit.ui.components.AddRow
import com.brunovt.ineedit.ui.components.ItemCard
import com.brunovt.ineedit.ui.theme.LocalAppTokens

private const val FULL_ITEMS = 4

@Composable
fun AccordionLayout(
    needItems: List<Entry>,
    wantItems: List<Entry>,
    wishItems: List<Entry>,
    onItemTap: (Entry) -> Unit,
    onAddTap: (Column) -> Unit,
    onActiveColumnChange: (Column) -> Unit,
    onMoveToColumn: (String, Column) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expandedColumn by rememberSaveable { mutableStateOf(Column.NEED) }
    val tokens = LocalAppTokens.current

    LaunchedEffect(expandedColumn) {
        onActiveColumnChange(expandedColumn)
    }

    Column(modifier = modifier.fillMaxSize()) {
        AccordionHeader(
            title = stringResource(R.string.columns_need_title),
            subtitle = stringResource(R.string.columns_need_subtitle),
            column = Column.NEED,
            isExpanded = expandedColumn == Column.NEED,
            accentColor = tokens.needSolid,
            onHeaderClick = { expandedColumn = Column.NEED },
        )
        if (expandedColumn == Column.NEED) {
            AccordionContent(
                addCta = stringResource(R.string.columns_need_add_cta),
                emptyText = stringResource(R.string.columns_need_empty),
                items = needItems,
                accentColor = tokens.needSolid,
                borderColor = tokens.needBorder,
                onItemTap = onItemTap,
                onAddTap = { onAddTap(Column.NEED) },
                onMoveToColumn = onMoveToColumn,
                modifier = Modifier.weight(1f),
            )
        }

        AccordionHeader(
            title = stringResource(R.string.columns_want_title),
            subtitle = stringResource(R.string.columns_want_subtitle),
            column = Column.WANT,
            isExpanded = expandedColumn == Column.WANT,
            accentColor = tokens.wantSolid,
            onHeaderClick = { expandedColumn = Column.WANT },
        )
        if (expandedColumn == Column.WANT) {
            AccordionContent(
                addCta = stringResource(R.string.columns_want_add_cta),
                emptyText = stringResource(R.string.columns_want_empty),
                items = wantItems,
                accentColor = tokens.wantSolid,
                borderColor = tokens.wantBorder,
                onItemTap = onItemTap,
                onAddTap = { onAddTap(Column.WANT) },
                onMoveToColumn = onMoveToColumn,
                modifier = Modifier.weight(1f),
            )
        }

        AccordionHeader(
            title = stringResource(R.string.columns_wish_title),
            subtitle = stringResource(R.string.columns_wish_subtitle),
            column = Column.WISH,
            isExpanded = expandedColumn == Column.WISH,
            accentColor = tokens.wishSolid,
            onHeaderClick = { expandedColumn = Column.WISH },
        )
        if (expandedColumn == Column.WISH) {
            AccordionContent(
                addCta = stringResource(R.string.columns_wish_add_cta),
                emptyText = stringResource(R.string.columns_wish_empty),
                items = wishItems,
                accentColor = tokens.wishSolid,
                borderColor = tokens.wishBorder,
                onItemTap = onItemTap,
                onAddTap = { onAddTap(Column.WISH) },
                onMoveToColumn = onMoveToColumn,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun AccordionHeader(
    title: String,
    subtitle: String,
    column: Column,
    isExpanded: Boolean,
    accentColor: Color,
    onHeaderClick: () -> Unit,
) {
    val dnd = LocalDragDrop.current
    val isDropTarget = dnd?.isDragging == true && dnd.hoveredColumn == column

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .sizeIn(minHeight = 56.dp)
            .background(
                if (isDropTarget) accentColor.copy(alpha = 0.15f)
                else Color.Transparent,
            )
            .onGloballyPositioned { coords ->
                val pos = coords.positionInWindow()
                val size = coords.size
                dnd?.registerDropZone(
                    column,
                    Rect(pos.x, pos.y, pos.x + size.width, pos.y + size.height),
                )
            }
            .clickable(onClick = onHeaderClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = accentColor,
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelMedium,
                color = accentColor.copy(alpha = 0.7f),
            )
        }
        Icon(
            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
            contentDescription = null,
            tint = if (isDropTarget) accentColor else accentColor.copy(alpha = 0.7f),
        )
    }
}

@Composable
private fun AccordionContent(
    addCta: String,
    emptyText: String,
    items: List<Entry>,
    accentColor: Color,
    borderColor: Color,
    onItemTap: (Entry) -> Unit,
    onAddTap: () -> Unit,
    onMoveToColumn: (String, Column) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
    ) {
        if (items.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = emptyText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = accentColor.copy(alpha = 0.6f),
                    )
                }
            }
        } else {
            itemsIndexed(items, key = { _, entry -> entry.id }) { index, entry ->
                ItemCard(
                    entry = entry,
                    borderColor = borderColor,
                    onClick = { onItemTap(entry) },
                    onMoveToColumn = onMoveToColumn,
                    initialExpanded = index < FULL_ITEMS,
                    modifier = Modifier.padding(vertical = 4.dp),
                )
            }
        }
        item {
            AddRow(
                label = addCta,
                accentColor = accentColor,
                onClick = onAddTap,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
