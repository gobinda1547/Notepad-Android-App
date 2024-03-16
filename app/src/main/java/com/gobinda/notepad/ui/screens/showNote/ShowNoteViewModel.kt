package com.gobinda.notepad.ui.screens.showNote

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowNoteViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {

    init {
        viewModelScope.launch {
            val noteId = savedStateHandle.get<Long>("noteId") ?: return@launch
            Log.i(TAG, "passed note id : $noteId")
        }
    }

    companion object {
        private const val TAG = "ShowNoteViewModel"
    }
}