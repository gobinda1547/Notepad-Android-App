package com.gobinda.notepad.ui.screens.addEditNote

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gobinda.notepad.domain.model.Note
import com.gobinda.notepad.domain.usecase.AddNoteException
import com.gobinda.notepad.domain.usecase.AddNoteUseCase
import com.gobinda.notepad.domain.usecase.GetSingleNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    getSingleNoteUseCase: GetSingleNoteUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val savedState: SavedStateHandle
) : ViewModel() {

    private val _contentText = MutableStateFlow("")

    /**
     * This attribute will be used to track the content
     */
    val contentText: StateFlow<String> = _contentText

    private val _toastMessage = MutableSharedFlow<Int?>()

    /**
     * This attribute will be used to pass events to UI layer when there
     * will be needed to show a toast message. Directly the value of the
     * string reference will be passed as integer.
     */
    val toastMessage: SharedFlow<Int?> = _toastMessage

    private val _shouldCloseScreen = MutableSharedFlow<Boolean?>()

    /**
     * This attribute represent that current screen should be closed.
     * Generally this will be happen when the add or update operation will
     * be successful.
     */
    val shouldCloseScreen: SharedFlow<Boolean?> = _shouldCloseScreen

    /**
     * This attribute will hold the currentNoteId that we are adding or
     * editing, when adding new note this attribute will hold -1, and when
     * we are editing a note then this attribute will hold the noteId value
     * of that corresponding note. It will be initialized by [savedState],
     * as soon as the view model will be created.
     */
    private var relatedNoteId: Long = -1L

    private val _isEditingNote = MutableStateFlow<Boolean?>(null)

    /**
     * We are directly exposing this attribute to UI layer so that it may
     * have the data to define whether we are adding a new note or we are
     * just editing a previous one. By depending on this value UI layer may
     * have to change it's title.
     */
    val isEditingNote: StateFlow<Boolean?> = _isEditingNote

    init {
        viewModelScope.launch {
            relatedNoteId = savedState.get<Long>("noteId") ?: -1L
            _isEditingNote.emit(relatedNoteId != -1L)
            if (relatedNoteId == -1L) return@launch // -1 for add note screen
            getSingleNoteUseCase.execute(relatedNoteId).firstOrNull()?.let {
                _contentText.emit(it.content)
            }
        }
    }

    fun handleEvent(event: AddEditUiEvent) = viewModelScope.launch {
        when (event) {
            is AddEditUiEvent.UpdateContent -> {
                _contentText.tryEmit(event.content)
            }

            AddEditUiEvent.SaveNote -> {
                try {
                    val currentNote = Note(
                        content = contentText.value,
                        lastEditTime = System.currentTimeMillis(),
                        id = if (relatedNoteId == -1L) 0L else relatedNoteId
                    )
                    addNoteUseCase.execute(currentNote)
                    _shouldCloseScreen.emit(true)
                } catch (exception: AddNoteException) {
                    _toastMessage.emit(exception.reason)
                }
            }
        }
    }
}