package com.brunovt.ineedit.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.brunovt.ineedit.R

@Composable
fun AppBottomNav(
    currentRoute: String,
    onNavigate: (String) -> Unit,
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == "dashboard",
            onClick = { onNavigate("dashboard") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(R.string.nav_dashboard),
                )
            },
            label = { Text(text = stringResource(R.string.nav_dashboard)) },
        )
        NavigationBarItem(
            selected = currentRoute == "done",
            onClick = { onNavigate("done") },
            icon = {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = stringResource(R.string.nav_history),
                )
            },
            label = { Text(text = stringResource(R.string.nav_history)) },
        )
        NavigationBarItem(
            selected = currentRoute == "settings",
            onClick = { onNavigate("settings") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(R.string.nav_settings),
                )
            },
            label = { Text(text = stringResource(R.string.nav_settings)) },
        )
    }
}
