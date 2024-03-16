package com.gobinda.notepad.data.repository

import com.gobinda.notepad.data.model.NoteModel
import com.gobinda.notepad.data.source.NoteDao
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {
    override fun getAllNotes(): Flow<List<NoteModel>> {
        return noteDao.getAllNotes()
    }

    override fun getSingleNote(noteId: Long): Flow<NoteModel?> {
        return noteDao.getSingleNote(noteId)
    }

    override suspend fun addOrUpdateNote(note: NoteModel): Long {
        return noteDao.addOrUpdateNote(note)
    }

    override suspend fun deleteNote(noteId: Long): Int {
        return noteDao.deleteNote(noteId)
    }
}