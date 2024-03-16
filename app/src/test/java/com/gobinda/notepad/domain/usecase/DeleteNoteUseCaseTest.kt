package com.gobinda.notepad.domain.usecase

import com.gobinda.notepad.R
import com.gobinda.notepad.data.repository.NoteRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteNoteUseCaseTest {

    private lateinit var repository: NoteRepository
    private lateinit var useCase: DeleteNoteUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = DeleteNoteUseCase(repository)
    }

    @Test
    fun tryingToDeleteAnUnknownItem() = runBlocking {
        val testNoteId = 5L
        coEvery { repository.deleteNote(testNoteId) } returns 0

        val isDeleteExceptionFound = try {
            useCase.execute(testNoteId)
            false
        } catch (e: DeleteNoteException) {
            TestCase.assertEquals(e.reason, R.string.error_unknown_exception)
            true
        } catch (e: Exception) {
            false
        }
        TestCase.assertEquals(true, isDeleteExceptionFound)
    }

    @Test
    fun tryingToDeleteAValidItem() = runBlocking {
        val testNoteId = 5L
        coEvery { repository.deleteNote(testNoteId) } returns 1

        val isExceptionFound = try {
            useCase.execute(testNoteId)
            false
        } catch (e: Exception) {
            true
        }
        TestCase.assertEquals(false, isExceptionFound)
    }
}