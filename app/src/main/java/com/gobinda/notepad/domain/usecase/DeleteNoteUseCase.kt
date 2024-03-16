package com.gobinda.notepad.domain.usecase

import com.gobinda.notepad.R
import com.gobinda.notepad.data.repository.NoteRepository

/**
 * Use this class to delete a note from database. Check [execute] API
 * for more details.
 */
class DeleteNoteUseCase(private val repository: NoteRepository) {

    /**
     * Tries to delete note with the input noteId. Throws exception
     * if the operation is not successful. Generally this function checks
     * whether the database operation is successful or not. Even if you
     * pass a noteId which is not present in the database then this function
     * will throw exception.
     *
     * @param noteId The noteId that you want to delete from the database.
     * @throws DeleteNoteException when the delete operation is not successful.
     */
    @Throws(DeleteNoteException::class)
    suspend fun execute(noteId: Long) {
        if (repository.deleteNote(noteId) <= 0) {
            throw DeleteNoteException(R.string.error_unknown_exception)
        }
    }
}

/**
 * This exception class will be used by [DeleteNoteUseCase] class when
 * the deletion operation will be failed. Check the delete API
 * [DeleteNoteUseCase.execute] for more details.
 */
class DeleteNoteException(val reason: Int) : Exception()