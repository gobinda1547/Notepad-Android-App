package com.gobinda.notepad.ui.screens.showNote

import androidx.lifecycle.SavedStateHandle
import com.gobinda.notepad.domain.model.Note
import com.gobinda.notepad.domain.usecase.DeleteNoteException
import com.gobinda.notepad.domain.usecase.DeleteNoteUseCase
import com.gobinda.notepad.domain.usecase.GetSingleNoteUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ShowNoteViewModelTest {

    private lateinit var viewModel: ShowNoteViewModel
    private lateinit var deleteNoteUseCase: DeleteNoteUseCase
    private lateinit var getSingleNoteUseCase: GetSingleNoteUseCase
    private lateinit var savedStateHandle: SavedStateHandle

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        deleteNoteUseCase = mockk()
        getSingleNoteUseCase = mockk()
        savedStateHandle = mockk()

        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testingByInitiatingRelativeNote() = runTest {
        val testNote = Note(
            id = 1L,
            content = "note content",
            lastEditTime = 100L
        )
        every { savedStateHandle.get<Long>("noteId") } returns testNote.id
        coEvery { getSingleNoteUseCase.execute(any()) } returns flowOf(testNote)

        viewModel = ShowNoteViewModel(getSingleNoteUseCase, deleteNoteUseCase, savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        TestCase.assertEquals(testNote, viewModel.currentNote.first())
    }

    @Test
    fun testingByTryingToDeleteNoteFailedCase() = runTest {
        val testNote = Note(
            id = 1L,
            content = "note content",
            lastEditTime = 100L
        )
        every { savedStateHandle.get<Long>("noteId") } returns testNote.id
        coEvery { getSingleNoteUseCase.execute(any()) } returns flowOf(testNote)
        coEvery { deleteNoteUseCase.execute(any()) } throws DeleteNoteException(1)

        viewModel = ShowNoteViewModel(getSingleNoteUseCase, deleteNoteUseCase, savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        var toastMessageId = -1
        val toastCollectionJob = launch {
            viewModel.toastMessage.collect { messageId ->
                messageId?.let { toastMessageId = it }
            }
        }

        viewModel.handleEvent(ShowNoteUiEvent.DeleteNote(testNote.id))
        testDispatcher.scheduler.advanceUntilIdle()

        toastCollectionJob.cancel()

        TestCase.assertEquals(1, toastMessageId)
    }

    @Test
    fun testingByTryingToDeleteNoteSuccessfulCase() = runTest {
        val testNote = Note(
            id = 1L,
            content = "note content",
            lastEditTime = 100L
        )
        every { savedStateHandle.get<Long>("noteId") } returns testNote.id
        coEvery { getSingleNoteUseCase.execute(any()) } returns flowOf(testNote)
        coEvery { deleteNoteUseCase.execute(any()) } returns Unit

        viewModel = ShowNoteViewModel(getSingleNoteUseCase, deleteNoteUseCase, savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        var shouldCloseScreenValue = false
        val screenCloseValueCollectorJob = launch {
            viewModel.shouldCloseScreen.collect { shouldClose ->
                shouldClose?.let { shouldCloseScreenValue = it }
            }
        }

        viewModel.handleEvent(ShowNoteUiEvent.DeleteNote(testNote.id))
        testDispatcher.scheduler.advanceUntilIdle()

        screenCloseValueCollectorJob.cancel()

        TestCase.assertEquals(true, shouldCloseScreenValue)
    }
}