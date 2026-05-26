package com.brunovt.ineedit.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
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
import com.brunovt.ineedit.data.prefs.ThemeMode
import com.brunovt.ineedit.domain.model.Status
import com.brunovt.ineedit.domain.model.Tag
import com.brunovt.ineedit.ui.components.AppBottomNav
import com.brunovt.ineedit.ui.components.ThemeCardPicker
import com.brunovt.ineedit.ui.theme.LocalAppTokens

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SettingsScreen(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val themeFamily by viewModel.themeFamily.collectAsStateWithLifecycle()
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
    val locale by viewModel.locale.collectAsStateWithLifecycle()
    val tags by viewModel.tags.collectAsStateWithLifecycle()
    val statuses by viewModel.statuses.collectAsStateWithLifecycle()
    val newTagText by viewModel.newTagText.collectAsStateWithLifecycle()
    val newStatusText by viewModel.newStatusText.collectAsStateWithLifecycle()
    val tokens = LocalAppTokens.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.settings_title)) },
            )
        },
        bottomBar = {
            AppBottomNav(
                currentRoute = currentRoute,
                onNavigate = onNavigate,
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Text(
                text = stringResource(R.string.settings_language),
                style = MaterialTheme.typography.titleMedium,
                color = tokens.inkDefault,
            )
            Spacer(modifier = Modifier.height(8.dp))
            SingleChoiceSegmentedButtonRow {
                SegmentedButton(
                    selected = locale == "es",
                    onClick = { viewModel.setLocale("es") },
                    shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                ) {
                    Text(text = stringResource(R.string.settings_language_es))
                }
                SegmentedButton(
                    selected = locale == "en",
                    onClick = { viewModel.setLocale("en") },
                    shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                ) {
                    Text(text = stringResource(R.string.settings_language_en))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.settings_theme),
                style = MaterialTheme.typography.titleMedium,
                color = tokens.inkDefault,
            )
            Spacer(modifier = Modifier.height(8.dp))
            ThemeCardPicker(
                selectedFamily = themeFamily,
                onSelect = viewModel::setThemeFamily,
            )

            Spacer(modifier = Modifier.height(16.dp))

            SingleChoiceSegmentedButtonRow {
                SegmentedButton(
                    selected = themeMode == ThemeMode.LIGHT,
                    onClick = { viewModel.setThemeMode(ThemeMode.LIGHT) },
                    shape = SegmentedButtonDefaults.itemShape(index = 0, count = 3),
                ) {
                    Text(text = stringResource(R.string.settings_theme_light))
                }
                SegmentedButton(
                    selected = themeMode == ThemeMode.SYSTEM,
                    onClick = { viewModel.setThemeMode(ThemeMode.SYSTEM) },
                    shape = SegmentedButtonDefaults.itemShape(index = 1, count = 3),
                ) {
                    Text(text = stringResource(R.string.settings_theme_auto))
                }
                SegmentedButton(
                    selected = themeMode == ThemeMode.DARK,
                    onClick = { viewModel.setThemeMode(ThemeMode.DARK) },
                    shape = SegmentedButtonDefaults.itemShape(index = 2, count = 3),
                ) {
                    Text(text = stringResource(R.string.settings_theme_dark))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.settings_tags),
                style = MaterialTheme.typography.titleMedium,
                color = tokens.inkDefault,
            )
            Spacer(modifier = Modifier.height(8.dp))
            TagChipEditor(
                items = tags,
                newText = newTagText,
                placeholder = stringResource(R.string.settings_tag_placeholder),
                onNewTextChange = viewModel::onNewTagTextChange,
                onAdd = viewModel::addTag,
                onDelete = viewModel::deleteTag,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.settings_statuses),
                style = MaterialTheme.typography.titleMedium,
                color = tokens.inkDefault,
            )
            Spacer(modifier = Modifier.height(8.dp))
            StatusChipEditor(
                items = statuses,
                newText = newStatusText,
                placeholder = stringResource(R.string.settings_status_placeholder),
                onNewTextChange = viewModel::onNewStatusTextChange,
                onAdd = viewModel::addStatus,
                onDelete = viewModel::deleteStatus,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.settings_sync),
                style = MaterialTheme.typography.titleMedium,
                color = tokens.inkDefault,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.settings_offline),
                style = MaterialTheme.typography.bodyMedium,
                color = tokens.inkFaded,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.settings_about),
                style = MaterialTheme.typography.titleMedium,
                color = tokens.inkDefault,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.settings_version),
                style = MaterialTheme.typography.bodyMedium,
                color = tokens.inkFaded,
            )

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TagChipEditor(
    items: List<Tag>,
    newText: String,
    placeholder: String,
    onNewTextChange: (String) -> Unit,
    onAdd: () -> Unit,
    onDelete: (String) -> Unit,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items.forEach { tag ->
            FilterChip(
                selected = false,
                onClick = {},
                label = { Text(text = tag.name) },
                trailingIcon = {
                    IconButton(
                        onClick = { onDelete(tag.id) },
                        modifier = Modifier.size(16.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            modifier = Modifier.size(12.dp),
                        )
                    }
                },
                colors = FilterChipDefaults.filterChipColors(),
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        OutlinedTextField(
            value = newText,
            onValueChange = onNewTextChange,
            placeholder = { Text(text = placeholder) },
            modifier = Modifier.weight(1f),
            singleLine = true,
        )
        IconButton(onClick = onAdd) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun StatusChipEditor(
    items: List<Status>,
    newText: String,
    placeholder: String,
    onNewTextChange: (String) -> Unit,
    onAdd: () -> Unit,
    onDelete: (String) -> Unit,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items.forEach { status ->
            FilterChip(
                selected = false,
                onClick = {},
                label = { Text(text = status.name) },
                trailingIcon = {
                    IconButton(
                        onClick = { onDelete(status.id) },
                        modifier = Modifier.size(16.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            modifier = Modifier.size(12.dp),
                        )
                    }
                },
                colors = FilterChipDefaults.filterChipColors(),
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        OutlinedTextField(
            value = newText,
            onValueChange = onNewTextChange,
            placeholder = { Text(text = placeholder) },
            modifier = Modifier.weight(1f),
            singleLine = true,
        )
        IconButton(onClick = onAdd) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }
}
