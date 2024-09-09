package com.example.notesapp.note_list.domain.use_case

import com.example.notesapp.core.domain.model.Note
import com.example.notesapp.core.domain.repository.NoteRepository
import javax.inject.Inject

class GetNotesSortedByDateAdded @Inject constructor(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(): List<Note> = noteRepository.getNotesSortedByDateAdded()

}