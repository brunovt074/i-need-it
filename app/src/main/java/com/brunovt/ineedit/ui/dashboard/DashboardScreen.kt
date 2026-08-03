package com.brunovt.ineedit.ui.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brunovt.ineedit.R
import com.brunovt.ineedit.data.prefs.LayoutStyle
import com.brunovt.ineedit.domain.model.Column
import com.brunovt.ineedit.domain.model.Entry
import com.brunovt.ineedit.ui.components.AppBottomNav
import com.brunovt.ineedit.ui.components.AppTopBar
import com.brunovt.ineedit.ui.components.ItemCard
import com.brunovt.ineedit.ui.theme.LocalAppTokens

@Composable
fun DashboardScreen(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onOpenEntry: (entryId: String?, column: Column) -> Unit,
    viewModel: DashboardViewModel = hiltViewModel(),
) {
    val needItems by viewModel.needItems.collectAsStateWithLifecycle()
    val wantItems by viewModel.wantItems.collectAsStateWithLifecycle()
    val wishItems by viewModel.wishItems.collectAsStateWithLifecycle()
    val layoutStyle by viewModel.layoutStyle.collectAsStateWithLifecycle()
    val activeColumn by viewModel.activeColumn.collectAsStateWithLifecycle()
    val tokens = LocalAppTokens.current
    val isFanLayout = layoutStyle == LayoutStyle.FAN

    val dnd = rememberDragDropController()

    val columnColor = when (activeColumn) {
        Column.NEED -> tokens.needSolid
        Column.WANT -> tokens.wantSolid
        Column.WISH -> tokens.wishSolid
    }

    CompositionLocalProvider(LocalDragDrop provides dnd) {
        Scaffold(
            topBar = {
                AppTopBar(
                    onSearchClick = {},
                    onSettingsClick = { onNavigate("settings") },
                    onLayoutToggle = viewModel::toggleLayout,
                    isFanLayout = isFanLayout,
                )
            },
            bottomBar = {
                AppBottomNav(
                    currentRoute = currentRoute,
                    onNavigate = onNavigate,
                )
            },
            floatingActionButton = {
                if (!dnd.isDragging) {
                    FloatingActionButton(
                        onClick = { onOpenEntry(null, activeColumn) },
                        containerColor = columnColor,
                        contentColor = Color.White,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.action_add),
                        )
                    }
                }
            },
        ) { padding ->
            val handleItemTap: (Entry) -> Unit = remember(onOpenEntry) {
                { entry -> onOpenEntry(entry.id, entry.column) }
            }
            val handleAddTap: (Column) -> Unit = remember(onOpenEntry) {
                { column -> onOpenEntry(null, column) }
            }
            val handleMove: (String, Column) -> Unit = remember(viewModel) {
                { id, col -> viewModel.moveEntry(id, col) }
            }

            var rootPosition by remember { mutableStateOf(Offset.Zero) }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .onGloballyPositioned { coords ->
                        rootPosition = coords.positionInWindow()
                    },
            ) {
                if (isFanLayout) {
                    FanLayout(
                        needItems = needItems,
                        wantItems = wantItems,
                        wishItems = wishItems,
                        onItemTap = handleItemTap,
                        onAddTap = handleAddTap,
                        onActiveColumnChange = viewModel::setActiveColumn,
                        onMoveToColumn = handleMove,
                        modifier = Modifier.fillMaxSize(),
                    )
                } else {
                    AccordionLayout(
                        needItems = needItems,
                        wantItems = wantItems,
                        wishItems = wishItems,
                        onItemTap = handleItemTap,
                        onAddTap = handleAddTap,
                        onActiveColumnChange = viewModel::setActiveColumn,
                        onMoveToColumn = handleMove,
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                if (dnd.isDragging) {
                    dnd.draggingEntry?.let { entry ->
                        val density = LocalDensity.current
                        val localX = dnd.dragPosition.x - rootPosition.x
                        val localY = dnd.dragPosition.y - rootPosition.y
                        val halfCardWidth = with(density) { 110.dp.toPx() }
                        val fingerOffsetY = with(density) { 12.dp.toPx() }

                        val borderColor = when (entry.column) {
                            Column.NEED -> tokens.needSolid
                            Column.WANT -> tokens.wantSolid
                            Column.WISH -> tokens.wishSolid
                        }

                        CompositionLocalProvider(LocalDragDrop provides null) {
                            ItemCard(
                                entry = entry,
                                borderColor = borderColor,
                                onClick = {},
                                onMoveToColumn = null,
                                modifier = Modifier
                                    .widthIn(max = 220.dp)
                                    .offset {
                                        IntOffset(
                                            (localX - halfCardWidth).toInt(),
                                            (localY - fingerOffsetY).toInt(),
                                        )
                                    }
                                    .graphicsLayer {
                                        alpha = 0.85f
                                        scaleX = 1.03f
                                        scaleY = 1.03f
                                        shadowElevation = 12f
                                    },
                            )
                        }
                    }
                }
            }
        }
    }
}
