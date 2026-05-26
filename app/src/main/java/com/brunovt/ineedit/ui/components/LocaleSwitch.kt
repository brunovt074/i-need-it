package com.brunovt.ineedit.ui.components

import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.brunovt.ineedit.R

@Composable
fun LocaleSwitch(
    selectedLocale: String,
    onLocaleSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        SegmentedButton(
            selected = selectedLocale == "es",
            onClick = { onLocaleSelect("es") },
            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
        ) {
            Text(text = stringResource(R.string.settings_language_es))
        }
        SegmentedButton(
            selected = selectedLocale == "en",
            onClick = { onLocaleSelect("en") },
            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
        ) {
            Text(text = stringResource(R.string.settings_language_en))
        }
    }
}
