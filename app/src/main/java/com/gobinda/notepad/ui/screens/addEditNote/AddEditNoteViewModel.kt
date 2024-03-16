package com.gobinda.notepad.ui.screens.addEditNote

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val savedState: SavedStateHandle
) : ViewModel() {

    init {
        viewModelScope.launch {
            val noteId = savedState.get<Long>("noteId") ?: -1L
            Log.i(TAG, "passed note id : $noteId")
        }
    }

    companion object {
        private const val TAG = "AddEditNoteViewModel"
    }
}