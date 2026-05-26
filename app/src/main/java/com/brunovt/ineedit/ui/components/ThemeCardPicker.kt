package com.brunovt.ineedit.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.brunovt.ineedit.R
import com.brunovt.ineedit.data.prefs.ThemeFamily

@Composable
fun ThemeCardPicker(
    selectedFamily: ThemeFamily,
    onSelect: (ThemeFamily) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ThemePreviewCard(
            label = stringResource(R.string.settings_theme_paper),
            isSelected = selectedFamily == ThemeFamily.PAPER,
            bgColor = Color(0xFFF1E8D4),
            accentColors = listOf(
                Color(0xFF8A2818),
                Color(0xFFA04A37),
                Color(0xFFB6735A),
            ),
            onClick = { onSelect(ThemeFamily.PAPER) },
            modifier = Modifier.weight(1f),
        )
        ThemePreviewCard(
            label = stringResource(R.string.settings_theme_minimal),
            isSelected = selectedFamily == ThemeFamily.MINIMAL,
            bgColor = Color(0xFFFBFAF8),
            accentColors = listOf(
                Color(0xFF8B3A2E),
                Color(0xFF6B5544),
                Color(0xFF8E847A),
            ),
            onClick = { onSelect(ThemeFamily.MINIMAL) },
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun ThemePreviewCard(
    label: String,
    isSelected: Boolean,
    bgColor: Color,
    accentColors: List<Color>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        border = if (isSelected)
            BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        else
            BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.cardColors(containerColor = bgColor),
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                accentColors.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(color),
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFF1C1B17),
            )
        }
    }
}
