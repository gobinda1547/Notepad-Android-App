package com.gobinda.notepad.ui.screens.showNote

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gobinda.notepad.domain.model.Note
import com.gobinda.notepad.domain.usecase.DeleteNoteException
import com.gobinda.notepad.domain.usecase.DeleteNoteUseCase
import com.gobinda.notepad.domain.usecase.GetSingleNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowNoteViewModel @Inject constructor(
    getSingleNoteUseCase: GetSingleNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _currentNote = MutableStateFlow<Note?>(null)
    val currentNote: StateFlow<Note?> = _currentNote

    private val _shouldCloseScreen = MutableSharedFlow<Boolean?>()
    val shouldCloseScreen: SharedFlow<Boolean?> = _shouldCloseScreen

    private val _toastMessage = MutableSharedFlow<Int?>()
    val toastMessage: SharedFlow<Int?> = _toastMessage

    init {
        viewModelScope.launch {
            val noteId = savedStateHandle.get<Long>("noteId") ?: return@launch
            getSingleNoteUseCase.execute(noteId).collect { noteResult ->
                noteResult?.let { _currentNote.emit(it) }
            }
        }
    }

    fun handleEvent(uiEvent: ShowNoteUiEvent) = viewModelScope.launch {
        if (uiEvent is ShowNoteUiEvent.DeleteNote) {
            try {
                deleteNoteUseCase.execute(noteId = uiEvent.noteId)
                _shouldCloseScreen.emit(true)
            } catch (exception: DeleteNoteException) {
                _toastMessage.emit(exception.reason)
            }
        }
    }
}