package com.farshidabz.spnote.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.farshidabz.spnote.presentation.feature.home.HomeScreenRoute
import com.farshidabz.spnote.presentation.feature.notedetail.NoteDetailScreenRoute

/**
 * Navigation host for the app.
 * Defines navigation graph and screen composables.
 */
@Composable
fun SPNoteNavHost(
    navController: NavHostController,
    innerPadding: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Screen.Home.route) {
            HomeScreenRoute(
                onOpenNote = { id -> navController.navigate(Screen.NoteDetail.createRoute(id)) },
                onCreateNote = { navController.navigate(Screen.NoteDetail.createRoute(null)) }
            )
        }

        composable(Screen.Bookmarks.route) {
            Text("Favorites")
        }

        composable(Screen.Setting.route) {
            Text("Setting")
        }

        composable(
            route = Screen.NoteDetail.route,
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.IntType
                    defaultValue = -1
                },
            )
        ) { backStackEntry ->
            NoteDetailScreenRoute(onBack = {
                navController.popBackStack()
            })
        }
    }
}
