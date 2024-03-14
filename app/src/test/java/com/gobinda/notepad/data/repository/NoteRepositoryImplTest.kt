package com.gobinda.notepad.data.repository

import com.gobinda.notepad.data.model.NoteModel
import com.gobinda.notepad.data.source.NoteDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class NoteRepositoryImplTest {

    private lateinit var repository: NoteRepository
    private lateinit var noteDao: NoteDao

    @Before
    fun setup() {
        noteDao = mockk()
        repository = NoteRepositoryImpl(noteDao)
    }

    @Test
    fun `testing getAllNotes when the list is empty`() = runBlocking {
        coEvery { noteDao.getAllNotes() } returns flowOf(emptyList())

        val result = repository.getAllNotes().first()

        coVerify(exactly = 1) { noteDao.getAllNotes() }
        assert(result.isEmpty())
    }

    @Test
    fun `testing getAllNotes when the list has an item`() = runBlocking {
        val currentTime = System.currentTimeMillis()
        val testNoteObject = NoteModel(
            id = 10,
            title = "This is a test title",
            content = "This is a test content",
            lastEditTime = currentTime
        )

        coEvery { noteDao.getAllNotes() } returns flowOf(listOf(testNoteObject))
        val result = repository.getAllNotes().first()

        coVerify(exactly = 1) { noteDao.getAllNotes() }
        assert(result == listOf(testNoteObject))
    }

    @Test
    fun `testing getSingleNote`() = runBlocking {
        val currentTime = System.currentTimeMillis()
        val testNoteObject = NoteModel(
            id = 10,
            title = "This is a test title",
            content = "This is a test content",
            lastEditTime = currentTime
        )
        coEvery { noteDao.getSingleNote(any()) } returns flowOf(testNoteObject)

        val result = repository.getSingleNote(100).first()

        coVerify(exactly = 1) { noteDao.getSingleNote(any()) }
        assert(result == testNoteObject)
    }

    @Test
    fun `testing addOrUpdateNote`() = runBlocking {
        coEvery { noteDao.addOrUpdateNote(any()) } returns 100L

        val result = repository.addOrUpdateNote(
            NoteModel(
                id = 10,
                title = "This is a test title",
                content = "This is a test content",
                lastEditTime = System.currentTimeMillis()
            )
        )

        coVerify(exactly = 1) { noteDao.addOrUpdateNote(any()) }
        assert(result == 100L)
    }

    @Test
    fun `testing deleteNote`() = runBlocking {
        coEvery { noteDao.deleteNote(any()) } returns 1
        repository.deleteNote(200)
        coVerify(exactly = 1) { noteDao.deleteNote(any()) }
    }
}