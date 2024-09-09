package com.example.notesapp.note_list.domain.use_case

import com.example.notesapp.core.domain.repository.NoteRepository
import javax.inject.Inject

class GetNotesSortedByTitle @Inject constructor(private val repository: NoteRepository) {
    suspend operator fun invoke() = repository.getNotesSortedByTitle()

}