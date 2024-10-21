package com.gobinda.notepad.domain.model

data class Note(
    val id: Long = 0,
    val content: String,
    val lastEditTime: Long
)