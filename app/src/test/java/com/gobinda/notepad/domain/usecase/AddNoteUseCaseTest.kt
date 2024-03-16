package com.gobinda.notepad.domain.usecase

import com.gobinda.notepad.R
import com.gobinda.notepad.data.model.NoteModel
import com.gobinda.notepad.data.repository.NoteRepository
import com.gobinda.notepad.domain.model.Note
import io.mockk.CapturingSlot
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddNoteUseCaseTest {

    private lateinit var useCase: AddNoteUseCase
    private lateinit var repository: NoteRepository

    @Before
    fun setUp() {
        repository = mockk()
        useCase = AddNoteUseCase(repository)
    }

    @Test
    fun testing_WhenNoteTitleIsEmpty() = runBlocking {
        val testNoteId = 5L
        val testNote = Note(
            id = testNoteId,
            title = "",
            content = "test content",
            lastEditTime = System.currentTimeMillis()
        )

        val addNoteExceptionFound = try {
            useCase.execute(testNote)
            false
        } catch (e: AddNoteException) {
            TestCase.assertEquals(e.reason, R.string.error_empty_title)
            true
        } catch (e: Exception) {
            false
        }
        TestCase.assertEquals(true, addNoteExceptionFound)
    }

    @Test
    fun testing_WhenNoteContentIsEmpty() = runBlocking {
        val testNoteId = 5L
        val testNote = Note(
            id = testNoteId,
            title = "test title",
            content = "",
            lastEditTime = System.currentTimeMillis()
        )

        val addNoteExceptionFound = try {
            useCase.execute(testNote)
            false
        } catch (e: AddNoteException) {
            TestCase.assertEquals(e.reason, R.string.error_empty_content)
            true
        } catch (e: Exception) {
            false
        }
        TestCase.assertEquals(true, addNoteExceptionFound)
    }

    @Test
    fun testing_WhenDatabaseInternalError() = runBlocking {
        coEvery { repository.addOrUpdateNote(any()) } returns -1

        val testNoteId = 5L
        val testNote = Note(
            id = testNoteId,
            title = "test title",
            content = "test content",
            lastEditTime = System.currentTimeMillis()
        )

        val addNoteExceptionFound = try {
            useCase.execute(testNote)
            false
        } catch (e: AddNoteException) {
            TestCase.assertEquals(e.reason, R.string.error_unknown_exception)
            true
        } catch (e: Exception) {
            false
        }
        TestCase.assertEquals(true, addNoteExceptionFound)
    }

    @Test
    fun testing_SuccessfulCase() = runBlocking {
        val slot = CapturingSlot<NoteModel>()
        coEvery { repository.addOrUpdateNote(capture(slot)) } answers { slot.captured.id }

        val testNoteId = 5L
        val testNote = Note(
            id = testNoteId,
            title = "test title",
            content = "test content",
            lastEditTime = System.currentTimeMillis()
        )

        val anyExceptionFound = try {
            useCase.execute(testNote)
            false
        } catch (e: Exception) {
            true
        }
        TestCase.assertEquals(false, anyExceptionFound)
    }
}