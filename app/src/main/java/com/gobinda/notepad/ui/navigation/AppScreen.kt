package com.gobinda.notepad.ui.navigation

sealed class AppScreen(val router: String) {
    data object NoteListScreen : AppScreen("note_list_screen")
    data object AddOrEditNoteScreen : AppScreen("add_edit_note_screen")
    data object ShowNoteScreen : AppScreen("show_note_screen")
}