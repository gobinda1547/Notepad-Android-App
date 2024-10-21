package com.gobinda.notepad.ui.screens.addEditNote

import androidx.lifecycle.SavedStateHandle
import com.gobinda.notepad.domain.model.Note
import com.gobinda.notepad.domain.usecase.AddNoteException
import com.gobinda.notepad.domain.usecase.AddNoteUseCase
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
class AddEditNoteViewModelTest {

    private lateinit var viewModel: AddEditNoteViewModel
    private lateinit var addNoteUseCase: AddNoteUseCase
    private lateinit var getSingleNoteUseCase: GetSingleNoteUseCase
    private lateinit var savedStateHandle: SavedStateHandle

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        addNoteUseCase = mockk()
        getSingleNoteUseCase = mockk()
        savedStateHandle = mockk()

        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun instantiationTestWhenAddingNewNote() = runTest {
        every { savedStateHandle.get<Long>("noteId") } returns -1L
        viewModel = AddEditNoteViewModel(getSingleNoteUseCase, addNoteUseCase, savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        TestCase.assertEquals(false, viewModel.isEditingNote.first())
        TestCase.assertEquals("", viewModel.contentText.first())
    }

    @Test
    fun instantiationTestWhenEditingPreviousNote() = runTest {
        val testNote = Note(
            id = 5L,
            content = "content",
            lastEditTime = 100L
        )
        every { savedStateHandle.get<Long>("noteId") } returns testNote.id
        every { getSingleNoteUseCase.execute(any()) } returns flowOf(testNote)

        viewModel = AddEditNoteViewModel(getSingleNoteUseCase, addNoteUseCase, savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        TestCase.assertEquals(true, viewModel.isEditingNote.first())
        TestCase.assertEquals("content", viewModel.contentText.first())
    }

    @Test
    fun testingByChangingContentText() = runTest {
        every { savedStateHandle.get<Long>("noteId") } returns -1L
        viewModel = AddEditNoteViewModel(getSingleNoteUseCase, addNoteUseCase, savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.handleEvent(AddEditUiEvent.UpdateContent("nice content"))
        testDispatcher.scheduler.advanceUntilIdle()

        TestCase.assertEquals(false, viewModel.isEditingNote.first())
        TestCase.assertEquals("nice content", viewModel.contentText.first())
    }

    @Test
    fun testingByTryingToSaveInvalidNote() = runTest {
        every { savedStateHandle.get<Long>("noteId") } returns -1L
        coEvery { addNoteUseCase.execute(any()) } throws AddNoteException(1)

        var toastMessageId = -1
        val toastCollectionJob = launch {
            viewModel.toastMessage.collect { messageId ->
                messageId?.let { toastMessageId = it }
            }
        }

        viewModel = AddEditNoteViewModel(getSingleNoteUseCase, addNoteUseCase, savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.handleEvent(AddEditUiEvent.SaveNote)
        testDispatcher.scheduler.advanceUntilIdle()

        // we are canceling the job other wise the test won't be completed
        // cause a sub coroutine will always be running to catch the updated
        // value of the toast message shared flow.
        toastCollectionJob.cancel()

        TestCase.assertEquals(1, toastMessageId)
    }

    @Test
    fun testingByTryingToSaveValidNote() = runTest {
        every { savedStateHandle.get<Long>("noteId") } returns -1L
        coEvery { addNoteUseCase.execute(any()) } returns Unit

        var shouldCloseScreenValue = false
        val screenCloseValueCollectorJob = launch {
            viewModel.shouldCloseScreen.collect { shouldClose ->
                shouldClose?.let { shouldCloseScreenValue = it }
            }
        }

        viewModel = AddEditNoteViewModel(getSingleNoteUseCase, addNoteUseCase, savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.handleEvent(AddEditUiEvent.UpdateContent("nice content"))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.handleEvent(AddEditUiEvent.SaveNote)
        testDispatcher.scheduler.advanceUntilIdle()

        // we are canceling the job other wise the test won't be completed
        // cause a sub coroutine will always be running to catch the updated
        // value of the toast message shared flow.
        screenCloseValueCollectorJob.cancel()

        TestCase.assertEquals(true, shouldCloseScreenValue)
    }
}