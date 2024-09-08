package com.example.notesapp.core.domain.model

data class Note(
    var title: String,
    var description: String,
    var imageUrl: String,

    val dateAdded: Long,

    val id: Int = 0,
)