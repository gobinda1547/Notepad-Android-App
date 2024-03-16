package com.gobinda.notepad.ui.screens.showNote

sealed class ShowNoteUiEvent {
    class DeleteNote(val noteId: Long) : ShowNoteUiEvent()
}