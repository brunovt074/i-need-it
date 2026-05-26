package com.brunovt.ineedit.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.brunovt.ineedit.ui.theme.LocalAppTokens

@Composable
fun SelectionChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    accentColor: Color = LocalAppTokens.current.needSolid,
    modifier: Modifier = Modifier,
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(text = label) },
        modifier = modifier.sizeIn(minHeight = 48.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (selected) accentColor else MaterialTheme.colorScheme.outlineVariant,
        ),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = accentColor,
            selectedLabelColor = MaterialTheme.colorScheme.surface,
        ),
    )
}
