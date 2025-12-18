package com.farshidabz.spnote.presentation.navigation

/**
 * Navigation routes for the app.
 */
sealed class Screen(val route: String) {
    data object Home : Screen("home_screen")
    data object Bookmarks : Screen("bookmark_screen")
    data object Setting : Screen("setting_screen")
    data object NoteDetail : Screen("note?noteId={noteId}") {
        fun createRoute(noteId: Int? = null): String {
            return if (noteId == null) "note" else "note?noteId=$noteId"
        }
    }
}