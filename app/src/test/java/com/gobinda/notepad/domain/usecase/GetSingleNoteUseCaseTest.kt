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

class GetSingleNoteUseCaseTest {

    private lateinit var repository: NoteRepository
    private lateinit var useCase: GetSingleNoteUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetSingleNoteUseCase(repository)
    }

    @Test
    fun getSingleNoteWhenTheListIsEmpty() = runBlocking {
        val testNoteId = 5L
        coEvery { repository.getSingleNote(testNoteId) } returns flowOf(null)

        val singleNote = useCase.execute(testNoteId).first()
        TestCase.assertEquals(null, singleNote)
    }

    @Test
    fun getSingleNoteWhenTheListIsNotEmpty() = runBlocking {
        val testNoteId = 5L
        val testNote = NoteModel(
            id = testNoteId,
            content = "test content",
            lastEditTime = 100L
        )
        coEvery { repository.getSingleNote(testNoteId) } returns flowOf(testNote)

        val singleNote = useCase.execute(testNoteId).first()
        TestCase.assertEquals(testNote.id, singleNote?.id)
        TestCase.assertEquals(testNote.content, singleNote?.content)
        TestCase.assertEquals(testNote.lastEditTime, singleNote?.lastEditTime)
    }
}