package com.gobinda.notepad.ui.navigation

sealed class AppScreen(val router: String) {
    data object NoteListScreen : AppScreen("note_list_screen")
}