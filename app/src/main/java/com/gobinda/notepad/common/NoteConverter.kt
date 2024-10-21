package com.gobinda.notepad.common

import com.gobinda.notepad.data.model.NoteModel
import com.gobinda.notepad.domain.model.Note
import com.gobinda.notepad.domain.model.NoteAsListItem

/**
 * This is an extension function written on behave of [Note] class. And
 * This function basically converted [Note] object to [NoteModel] object.
 */
fun Note.toNoteModel(): NoteModel {
    return NoteModel(id, content, lastEditTime)
}

/**
 * This is an extension function written on behave of [NoteModel] class.
 * This function basically converted [NoteModel] object to [Note] object.
 */
fun NoteModel.toNote(): Note {
    return Note(id, content, lastEditTime)
}

/**
 * This extension function is written on behave of [NoteModel] class. This
 * function will convert the [NoteModel] object into an object of [NoteAsListItem]
 * class. As title we will select the first non empty line.
 */
fun NoteModel.toNoteAsListItem(): NoteAsListItem {
    return NoteAsListItem(
        title = content.split("\n").firstOrNull { it.isNotEmpty() } ?: "",
        id = id
    )
}