package com.example.notesapp.add_note.domain.use_case

import com.example.notesapp.core.domain.model.Note
import com.example.notesapp.core.domain.repository.NoteRepository
import javax.inject.Inject

class UpsertNote @Inject constructor(private val repository: NoteRepository) {

    suspend operator fun invoke(
        title: String,
        description: String,
        imageUrl: String,
        id: Int?
    ): Boolean {
        if (title.isBlank() || description.isBlank())
            return false

        val note = Note(
            id = id,
            title = title,
            description = description,
            dateAdded = System.currentTimeMillis(),
            imageUrl = imageUrl
        )



        repository.upsertNote(note)
        return true

    }
}