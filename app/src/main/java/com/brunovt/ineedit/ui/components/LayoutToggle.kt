package com.brunovt.ineedit.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.ViewAgenda
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.brunovt.ineedit.R

@Composable
fun LayoutToggle(
    isFanLayout: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onToggle,
        modifier = modifier,
    ) {
        if (isFanLayout) {
            Icon(
                imageVector = Icons.Default.ViewAgenda,
                contentDescription = stringResource(R.string.action_layout_list),
            )
        } else {
            Icon(
                imageVector = Icons.Default.TableChart,
                contentDescription = stringResource(R.string.action_layout_fan),
            )
        }
    }
}
