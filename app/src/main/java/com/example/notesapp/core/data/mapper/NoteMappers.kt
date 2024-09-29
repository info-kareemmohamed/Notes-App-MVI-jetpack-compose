package com.example.notesapp.core.data.mapper

import com.example.notesapp.core.data.local.NoteEntity
import com.example.notesapp.core.domain.model.Note

fun Note.toNoteEntity(
): NoteEntity {
    return NoteEntity(
        title = title,
        description = description,
        imageUrl = imageUrl,
        dateAdded = dateAdded,
        id = id
    )
}


fun NoteEntity.toNoteItem(): Note {
    return Note(
        title = title,
        description = description,
        imageUrl = imageUrl,
        dateAdded = dateAdded,
        id = id ?: 0
    )
}