package com.gobinda.notepad.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * The class definition that will be used by room database to save a note.
 */
@Entity(tableName = "note_table")
data class NoteModel(

    /**
     * We will preserve every [NoteModel] in room database with an unique id.
     * So that we can perform queries on different [NoteModel] specifically.
     * Also we won't have to worry about unique ID generation cause it will
     * be done by room itself.
     */
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    /**
     * This attribute will hold the title of the [NoteModel]. In data layer
     * we are not putting any specific length conditions for the title. User
     * can have 2 [NoteModel] with same [title].
     */
    @ColumnInfo(name = "title")
    val title: String,

    /**
     * This attribute will hold the content or body part of the [NoteModel].
     * Here also - no length limitation.
     */
    @ColumnInfo(name = "content")
    val content: String,

    /**
     * We need to store this info otherwise we won't be able to short the
     * note list according to last edit time. Every time when a new note will
     * be added in database then we have to add that particular [NoteModel]
     * with current time as the [lastEditTime]. On the other hand when a new
     * note item will be modified or updated then we also have to update the
     * [lastEditTime] with the current time at that particular time.
     */
    @ColumnInfo(name = "last_edit_time")
    val lastEditTime: Long
)