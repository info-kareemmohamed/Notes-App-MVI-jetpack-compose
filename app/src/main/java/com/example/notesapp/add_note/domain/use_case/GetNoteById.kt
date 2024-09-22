package com.example.notesapp.add_note.domain.use_case

import com.example.notesapp.core.domain.repository.NoteRepository
import javax.inject.Inject

class GetNoteById @Inject constructor(
    private val repository: NoteRepository

) {
    suspend operator fun invoke(id: Int) = repository.getNoteById(id)


}