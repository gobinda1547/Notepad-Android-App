package com.gobinda.notepad.data.repository

import com.gobinda.notepad.data.model.NoteModel
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    /**
     * Get all the notes from database.
     *
     * @return A flow of the list of notes
     */
    fun getAllNotes(): Flow<List<NoteModel>>

    /**
     * Get only one note from the database.
     *
     * @param noteId the id of the note that you are searching for.
     * @return A flow of a single note object.
     */
    fun getSingleNote(noteId: Long): Flow<NoteModel?>

    /**
     * Add new note or update previous one with the same API. When
     * we will add a new note then we will pass 0 as noteId and when
     * we will update a previous note then we will pass the previous
     * id as noteId. Room database will automatically REPLACE if the
     * id is duplicated.
     *
     * @param note the note object that we want to add or update.
     * @return the note id that got added or updated.
     */
    suspend fun addOrUpdateNote(note: NoteModel): Long

    /**
     * Delete a particular note by using the noteId.
     *
     * @param noteId the note id that you want to delete.
     * @return the row count that got affected by the delete command.
     */
    suspend fun deleteNote(noteId: Long): Int
}