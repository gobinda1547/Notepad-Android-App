package com.gobinda.notepad.domain.model

/**
 * You may have question like why this class is necessary cause we already
 * have a Note class for this with more parameters. But this class is created
 * with having mind that the title will have only one line. But when accessing
 * the Note object it won't have one line title. Then to full fill requirement
 * we have to modify the title into one line. So we want to perform this string
 * modification inside the view model. And the view model then return or update
 * the list of [NoteAsListItem] objects. We can use the existing Note class but
 * that won't be good cause we have to parse single line title every time when
 * loading the list.
 */
data class NoteAsListItem(
    /**
     * We need this attribute cause we will show title in the list of notes.
     */
    val title: String,

    /**
     * We need this attribute to identify which Note item was selected by the
     * user to perform specific operation.
     */
    val id: Long = 0
)