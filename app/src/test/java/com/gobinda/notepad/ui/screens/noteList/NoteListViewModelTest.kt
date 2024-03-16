package com.gobinda.notepad.ui.screens.noteList

import com.gobinda.notepad.domain.model.NoteAsListItem
import com.gobinda.notepad.domain.usecase.GetNotesUseCase
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NoteListViewModelTest {

    private lateinit var viewModel: NoteListViewModel
    private lateinit var getNotesUseCase: GetNotesUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        getNotesUseCase = mockk()
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testingWhenTheListIsEmpty() = runTest {
        every { getNotesUseCase.execute() } returns flowOf(emptyList())
        viewModel = NoteListViewModel(getNotesUseCase)
        testDispatcher.scheduler.advanceUntilIdle()
        val resultNoteList = viewModel.noteList.first()
        TestCase.assertEquals(0, resultNoteList?.size)
    }

    @Test
    fun testingWhenTheListIsNotEmpty() = runTest {
        val testNoteList = listOf(
            NoteAsListItem(
                id = 1L,
                title = "first"
            ), NoteAsListItem(
                id = 2L,
                title = "second"
            )
        )
        every { getNotesUseCase.execute() } returns flowOf(testNoteList)

        viewModel = NoteListViewModel(getNotesUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        val resultNoteList = viewModel.noteList.first()
        TestCase.assertEquals(2, resultNoteList?.size)
        TestCase.assertEquals(1L, resultNoteList?.get(0)?.id)
        TestCase.assertEquals("first", resultNoteList?.get(0)?.title)
        TestCase.assertEquals(2L, resultNoteList?.get(1)?.id)
        TestCase.assertEquals("second", resultNoteList?.get(1)?.title)
    }
}