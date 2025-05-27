package com.comicreader.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.comicreader.app.ui.screens.library.LibraryScreen
import com.comicreader.app.ui.screens.reader.ComicReaderScreen
import com.comicreader.app.ui.screens.settings.SettingsScreen

sealed class Screen(val route: String) {
    object Library : Screen("library")
    object Reader : Screen("reader/{comicId}") {
        fun createRoute(comicId: String) = "reader/$comicId"
    }
    object Settings : Screen("settings")
}

@Composable
fun ComicReaderNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Library.route
    ) {
        composable(Screen.Library.route) {
            LibraryScreen(
                onComicClick = { comicId ->
                    navController.navigate(Screen.Reader.createRoute(comicId))
                },
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }
        
        composable(Screen.Reader.route) { backStackEntry ->
            val comicId = backStackEntry.arguments?.getString("comicId") ?: ""
            ComicReaderScreen(
                comicId = comicId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Settings.route) {
            SettingsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
