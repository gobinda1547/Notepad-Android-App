package com.gobinda.notepad.ui.screens.noteList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gobinda.notepad.domain.model.NoteAsListItem
import com.gobinda.notepad.domain.usecase.GetNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(getNotesUseCase: GetNotesUseCase) : ViewModel() {

    private val _noteList = MutableStateFlow<List<NoteAsListItem>?>(null)
    val noteList: StateFlow<List<NoteAsListItem>?> = _noteList

    init {
        viewModelScope.launch {
            getNotesUseCase.execute().collect {
                _noteList.emit(it)
            }
        }
    }
}