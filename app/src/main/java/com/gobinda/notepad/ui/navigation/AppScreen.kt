package com.gobinda.notepad.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class AppScreen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {

    data object NoteListScreen : AppScreen(
        route = "note_list_screen"
    )

    data object AddOrEditNoteScreen : AppScreen(
        route = "add_edit_note_screen/{noteId}",
        navArguments = listOf(
            navArgument(name = "noteId") {
                type = NavType.LongType
            }
        )
    ) {
        fun createRoute(noteId: Long) = "add_edit_note_screen/${noteId}"
    }

    data object ShowNoteScreen : AppScreen(
        route = "show_note_screen/{noteId}",
        navArguments = listOf(
            navArgument(name = "noteId") {
                type = NavType.LongType
            }
        )
    ) {
        fun createRoute(noteId: Long) = "show_note_screen/${noteId}"
    }
}