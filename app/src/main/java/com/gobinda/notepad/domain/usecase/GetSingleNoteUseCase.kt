package com.gobinda.notepad.domain.usecase

import com.gobinda.notepad.common.toNote
import com.gobinda.notepad.data.repository.NoteRepository
import com.gobinda.notepad.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Use this class to get a single note by using it's noteId. Check [execute]
 * API for more details.
 */
class GetSingleNoteUseCase(private val repository: NoteRepository) {

    /**
     * This function returns the corresponding note object inside a flow object.
     *
     * @param noteId The noteId of the note object that you want in return.
     * @return The corresponding note object with a Flow wrapper
     */
    fun execute(noteId: Long): Flow<Note?> {
        return repository.getSingleNote(noteId).map { it?.toNote() }
    }
}