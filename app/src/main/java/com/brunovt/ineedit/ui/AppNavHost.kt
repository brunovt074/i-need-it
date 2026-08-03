package com.brunovt.ineedit.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.brunovt.ineedit.domain.model.Column
import com.brunovt.ineedit.ui.dashboard.DashboardScreen
import com.brunovt.ineedit.ui.history.DoneScreen
import com.brunovt.ineedit.ui.modal.EntryFormSheet
import com.brunovt.ineedit.ui.settings.SettingsScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route ?: "dashboard"

    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") {
            DashboardScreen(
                currentRoute = "dashboard",
                onNavigate = { route -> navController.navigate(route) },
                onOpenEntry = { id, col ->
                    val idPart = if (id != null) "entryId=$id&" else ""
                    navController.navigate("entry_form?${idPart}column=${col.name}")
                },
            )
        }
        composable(
            route = "entry_form?entryId={entryId}&column={column}",
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None },
            arguments = listOf(
                navArgument("entryId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument("column") {
                    type = NavType.StringType
                    defaultValue = Column.NEED.name
                },
            ),
        ) {
            EntryFormSheet(onDismiss = { navController.popBackStack() })
        }
        composable("done") {
            DoneScreen(
                currentRoute = "done",
                onNavigate = { route -> navController.navigate(route) },
            )
        }
        composable("settings") {
            SettingsScreen(
                currentRoute = "settings",
                onNavigate = { route -> navController.navigate(route) },
            )
        }
    }
}
