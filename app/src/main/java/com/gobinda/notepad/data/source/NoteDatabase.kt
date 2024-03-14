package com.gobinda.notepad.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gobinda.notepad.data.model.NoteModel

@Database(
    entities = [NoteModel::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        const val DB_NAME = "note_db"
    }
}