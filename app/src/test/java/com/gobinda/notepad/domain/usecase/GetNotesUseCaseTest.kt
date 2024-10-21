package com.gobinda.notepad.domain.usecase

import com.gobinda.notepad.data.model.NoteModel
import com.gobinda.notepad.data.repository.NoteRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNotesUseCaseTest {

    private lateinit var repository: NoteRepository
    private lateinit var useCase: GetNotesUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetNotesUseCase(repository)
    }

    @Test
    fun testingGetNoteApiWhenListIsEmpty() = runBlocking {
        coEvery { repository.getAllNotes() } returns flowOf(emptyList())
        val noteList = useCase.execute().first()
        TestCase.assertEquals(0, noteList.size)
    }

    @Test
    fun testingGetNoteApiWhenListIsNotEmpty() = runBlocking {
        val testNoteModelList = listOf(
            NoteModel(
                id = 1L,
                content = "First note content",
                lastEditTime = 101L
            ), NoteModel(
                id = 2L,
                content = "Second note content",
                lastEditTime = 102L
            )
        )
        coEvery { repository.getAllNotes() } returns flowOf(testNoteModelList)
        val noteList = useCase.execute().first()
        TestCase.assertEquals(2, noteList.size)

        // the list is sorted according to last edit time so the note whose id
        // is 2 will be the first item since it's edited last
        TestCase.assertEquals(2L, noteList[0].id)
        TestCase.assertEquals("Second note content", noteList[0].title)
        TestCase.assertEquals(1L, noteList[1].id)
        TestCase.assertEquals("First note content", noteList[1].title)
    }
}