package com.gobinda.notepad.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gobinda.notepad.data.model.NoteModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * from note_table")
    fun getAllNotes(): Flow<List<NoteModel>>

    @Query("SELECT * from note_table WHERE id = :noteId")
    fun getSingleNote(noteId: Long): Flow<NoteModel?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrUpdateNote(note: NoteModel): Long

    @Query("DELETE from note_table WHERE id = :noteId")
    suspend fun deleteNote(noteId: Long): Int
}