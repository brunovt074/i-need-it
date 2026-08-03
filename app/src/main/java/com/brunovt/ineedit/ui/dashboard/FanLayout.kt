package com.brunovt.ineedit.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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

@Composable
fun FanLayout(
    needItems: List<Entry>,
    wantItems: List<Entry>,
    wishItems: List<Entry>,
    onItemTap: (Entry) -> Unit,
    onAddTap: (Column) -> Unit,
    onActiveColumnChange: (Column) -> Unit,
    onMoveToColumn: (String, Column) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tokens = LocalAppTokens.current
    val dnd = LocalDragDrop.current
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(pageCount = { 3 })

    val tabs = listOf(
        Triple(stringResource(R.string.columns_need_title), needItems, tokens.needSolid),
        Triple(stringResource(R.string.columns_want_title), wantItems, tokens.wantSolid),
        Triple(stringResource(R.string.columns_wish_title), wishItems, tokens.wishSolid),
    )
    val addCtaLabels = listOf(
        stringResource(R.string.columns_need_add_cta),
        stringResource(R.string.columns_want_add_cta),
        stringResource(R.string.columns_wish_add_cta),
    )
    val emptyLabels = listOf(
        stringResource(R.string.columns_need_empty),
        stringResource(R.string.columns_want_empty),
        stringResource(R.string.columns_wish_empty),
    )
    val columns = listOf(Column.NEED, Column.WANT, Column.WISH)
    val borderColors = listOf(tokens.needBorder, tokens.wantBorder, tokens.wishBorder)

    LaunchedEffect(Unit) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selectedTab = page
            onActiveColumnChange(columns[page])
        }
    }

    LaunchedEffect(selectedTab) {
        if (pagerState.currentPage != selectedTab) {
            pagerState.animateScrollToPage(selectedTab)
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedTab,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = tabs[selectedTab].third,
                )
            },
        ) {
            tabs.forEachIndexed { index, (label, _, accentColor) ->
                val col = columns[index]
                val isDropTarget = dnd?.isDragging == true && dnd.hoveredColumn == col
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    modifier = Modifier
                        .background(
                            if (isDropTarget) accentColor.copy(alpha = 0.25f)
                            else Color.Transparent,
                        )
                        .onGloballyPositioned { coords ->
                            val pos = coords.positionInWindow()
                            val size = coords.size
                            dnd?.registerDropZone(
                                col,
                                Rect(pos.x, pos.y, pos.x + size.width, pos.y + size.height),
                            )
                        },
                    text = {
                        Text(
                            text = label,
                            color = if (isDropTarget) accentColor else accentColor.copy(alpha = if (selectedTab == index) 1f else 0.7f),
                        )
                    },
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
        ) { page ->
            val (_, currentItems, currentAccent) = tabs[page]
            val currentBorder = borderColors[page]
            val currentAddCta = addCtaLabels[page]
            val currentEmpty = emptyLabels[page]
            val currentColumn = columns[page]

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                if (currentItems.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = currentEmpty,
                                color = currentAccent.copy(alpha = 0.6f),
                            )
                        }
                    }
                } else {
                    items(currentItems, key = { it.id }) { entry ->
                        ItemCard(
                            entry = entry,
                            borderColor = currentBorder,
                            onClick = { onItemTap(entry) },
                            onMoveToColumn = onMoveToColumn,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        )
                    }
                }
                item {
                    AddRow(
                        label = currentAddCta,
                        accentColor = currentAccent,
                        onClick = { onAddTap(currentColumn) },
                    )
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}
