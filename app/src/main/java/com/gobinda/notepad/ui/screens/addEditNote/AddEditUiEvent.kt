package com.gobinda.notepad.ui.screens.addEditNote

sealed class AddEditUiEvent {
    class UpdateTitle(val title: String) : AddEditUiEvent()
    class UpdateContent(val content: String) : AddEditUiEvent()
    data object SaveNote : AddEditUiEvent()
}