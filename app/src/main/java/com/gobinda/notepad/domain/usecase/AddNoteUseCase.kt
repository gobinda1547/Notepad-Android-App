package com.gobinda.notepad.domain.usecase

import com.gobinda.notepad.R
import com.gobinda.notepad.common.toNoteModel
import com.gobinda.notepad.domain.model.Note
import com.gobinda.notepad.data.repository.NoteRepository

/**
 * Use this class to add new note in the database. Check [execute] API for
 * more details.
 */
class AddNoteUseCase(private val repository: NoteRepository) {

    /**
     * Checks whether the input note is ready to add in the database or not.
     * If the note has missing information then this function will return an
     * exception by mentioning that error. In general this function checks,
     * is the note title empty or not, is the note content empty or not, and
     * is the add operation successful or not.
     *
     * @param note the note you want to add in the database.
     * @throws AddNoteException when missing information in the input note
     */
    @Throws(AddNoteException::class)
    suspend fun execute(note: Note) {
        if (note.title.trim().isEmpty()) {
            throw AddNoteException(R.string.error_empty_title)
        }
        if (note.content.trim().isEmpty()) {
            throw AddNoteException(R.string.error_empty_content)
        }
        if (repository.addOrUpdateNote(note.toNoteModel()) <= 0) {
            throw AddNoteException(R.string.error_unknown_exception)
        }
    }
}

/**
 * This exception class will be used when user want to add a new note, but
 * that note has missing required information or all the fields are not
 * properly filled. Check [AddNoteUseCase] class for more details.
 */
class AddNoteException(val reason: Int) : Exception()