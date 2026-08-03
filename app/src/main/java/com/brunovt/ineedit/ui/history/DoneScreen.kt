package com.brunovt.ineedit.ui.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brunovt.ineedit.R
import com.brunovt.ineedit.domain.model.Entry
import com.brunovt.ineedit.ui.components.AppBottomNav
import com.brunovt.ineedit.ui.theme.LocalAppTokens
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoneScreen(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    viewModel: DoneViewModel = hiltViewModel(),
) {
    val items by viewModel.items.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.history_title)) },
            )
        },
        bottomBar = {
            AppBottomNav(
                currentRoute = currentRoute,
                onNavigate = onNavigate,
            )
        },
    ) { padding ->
        if (items.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(R.string.history_empty),
                    style = MaterialTheme.typography.bodyLarge,
                    color = LocalAppTokens.current.inkFaded,
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
            ) {
                items(items, key = { it.id }) { entry ->
                    DoneItemCard(
                        entry = entry,
                        onLongPress = { viewModel.restore(entry) },
                        modifier = Modifier.padding(vertical = 4.dp),
                    )
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DoneItemCard(
    entry: Entry,
    onLongPress: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val tokens = LocalAppTokens.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {},
                onLongClick = onLongPress,
            ),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = entry.name,
                style = MaterialTheme.typography.bodyLarge,
                color = tokens.inkDefault,
            )
            entry.completedAt?.let { instant ->
                val date = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.history_completed_on, date.toString()),
                    style = MaterialTheme.typography.labelMedium,
                    color = tokens.inkFaded,
                )
            }
        }
    }
}
