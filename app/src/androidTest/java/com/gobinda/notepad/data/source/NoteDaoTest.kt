package com.gobinda.notepad.data.source

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.gobinda.notepad.data.model.NoteModel
import junit.framework.TestCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class NoteDaoTest {

    private lateinit var noteDatabase: NoteDatabase
    private lateinit var noteDao: NoteDao

    @Before
    fun setup() {
        noteDatabase = Room.inMemoryDatabaseBuilder(
            context = ApplicationProvider.getApplicationContext(),
            klass = NoteDatabase::class.java
        ).allowMainThreadQueries().build()
        noteDao = noteDatabase.noteDao()
    }

    @After
    fun tearDown() {
        noteDatabase.close()
    }

    /**
     * Initially the database won't have any Note. We will add one Note then we will
     * retrieve that note by using getAllNote API & match different parameter.
     */
    @Test
    fun testing_GetAllNoteApi() = runBlocking {
        val testNoteId = 10L
        val noteList1 = noteDao.getAllNotes().first()
        TestCase.assertEquals(0, noteList1.size)

        val newNoteId = noteDao.addOrUpdateNote(
            NoteModel(
                id = testNoteId,
                title = "test title",
                content = "test content",
                lastEditTime = 100L
            )
        )

        TestCase.assertEquals(testNoteId, newNoteId)
        val noteList2 = noteDao.getAllNotes().first()
        TestCase.assertEquals(1, noteList2.size)
        TestCase.assertEquals(testNoteId, noteList2[0].id)
        TestCase.assertEquals("test title", noteList2[0].title)
        TestCase.assertEquals("test content", noteList2[0].content)
    }

    /**
     * Initially the database won't have any Note. We will add one Note then we will
     * retrieve that note by using getSingleNote API & match different parameter.
     */
    @Test
    fun testing_GetSingleNoteApi() = runBlocking {
        val testNoteId = 10L

        val testNote1 = noteDao.getSingleNote(testNoteId).first()
        TestCase.assertEquals(null, testNote1)

        val newNoteId = noteDao.addOrUpdateNote(
            NoteModel(
                id = testNoteId,
                title = "test title",
                content = "test content",
                lastEditTime = 100L
            )
        )

        TestCase.assertEquals(testNoteId, newNoteId)
        val testNote2 = noteDao.getSingleNote(testNoteId).first()
        TestCase.assertEquals(testNoteId, testNote2?.id)
        TestCase.assertEquals("test title", testNote2?.title)
        TestCase.assertEquals("test content", testNote2?.content)
    }

    /**
     * Our add API can replace in case note item already present in the database.
     * So we will just call add API and check the returned value to confirm that
     * out API works.
     */
    @Test
    fun testing_AddNoteApi() = runBlocking {
        val testNoteId = 10L
        val newNoteId = noteDao.addOrUpdateNote(
            NoteModel(
                id = testNoteId,
                title = "test title",
                content = "test content",
                lastEditTime = 100L
            )
        )

        TestCase.assertEquals(testNoteId, newNoteId)
    }

    /**
     * Our update API can replace in case note item already present in the database.
     * So we will just call update API and check the returned value to confirm that
     * out API works. But first of all we have to make a scenario that it's actually
     * a update operation.
     */
    @Test
    fun testing_UpdateNoteApi() = runBlocking {
        val testNoteId = 10L

        // first operation is add operation
        val newNoteId1 = noteDao.addOrUpdateNote(
            NoteModel(
                id = testNoteId,
                title = "test title",
                content = "test content",
                lastEditTime = 100L
            )
        )
        TestCase.assertEquals(testNoteId, newNoteId1)

        // second operation is update operation
        val newNoteId2 = noteDao.addOrUpdateNote(
            NoteModel(
                id = testNoteId,
                title = "test title",
                content = "test content",
                lastEditTime = 100L
            )
        )
        TestCase.assertEquals(testNoteId, newNoteId2)
    }

    /**
     * First of all we will insert an item into the list, later we will delete that
     * item to verify our API works.
     */
    @Test
    fun testing_DeleteNoteApi() = runBlocking {
        val testNoteId = 10L

        // this means add operation successful
        val newNoteId1 = noteDao.addOrUpdateNote(
            NoteModel(
                id = testNoteId,
                title = "test title",
                content = "test content",
                lastEditTime = 100L
            )
        )
        TestCase.assertEquals(testNoteId, newNoteId1)

        // this means delete operation successful
        val affectedRows = noteDao.deleteNote(testNoteId)
        TestCase.assertEquals(1, affectedRows)

        // also verified that the note is not present in the database
        val testNote1 = noteDao.getSingleNote(testNoteId).first()
        TestCase.assertEquals(null, testNote1)
    }
}