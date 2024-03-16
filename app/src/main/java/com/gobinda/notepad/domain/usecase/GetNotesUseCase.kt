package com.gobinda.notepad.domain.usecase

import com.gobinda.notepad.common.toNoteAsListItem
import com.gobinda.notepad.data.repository.NoteRepository
import com.gobinda.notepad.domain.model.NoteAsListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * To get all the notes use this class.
 */
class GetNotesUseCase(private val repository: NoteRepository) {

    /**
     * This function will return all the notes inside a list. All the notes
     * will be inserted into the list by descending order according to their
     * lastEditTime.
     */
    fun execute(): Flow<List<NoteAsListItem>> {
        return repository.getAllNotes().map { notes ->
            notes.sortedByDescending { it.lastEditTime }
        }.map { noteModelList -> noteModelList.map { it.toNoteAsListItem() } }
    }
}