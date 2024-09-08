package com.example.notesapp.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("note_entity")
data class NoteEntity(
    var title: String,
    var description: String,
    var imageUrl: String,

    var dateAdded: Long,

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
)