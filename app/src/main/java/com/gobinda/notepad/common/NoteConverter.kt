package com.gobinda.notepad.common

import com.gobinda.notepad.data.model.NoteModel
import com.gobinda.notepad.domain.model.Note

/**
 * This is an extension function written on behave of [Note] class. And
 * This function basically converted [Note] object to [NoteModel] object.
 */
fun Note.toNoteModel(): NoteModel {
    return NoteModel(id, title, content, lastEditTime)
}

/**
 * This is an extension function written on behave of [NoteModel] class.
 * This function basically converted [NoteModel] object to [Note] object.
 */
fun NoteModel.toNote(): Note {
    return Note(id, title, content, lastEditTime)
}