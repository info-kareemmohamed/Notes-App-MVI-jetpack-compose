package com.example.notesapp.note_list.presentation.mvi

import com.example.notesapp.core.domain.model.Note

sealed class NoteListIntent {
    object LoadNotes : NoteListIntent()
    object SortNotesByTitle : NoteListIntent()
    object SortNotesByDateAdded : NoteListIntent()
    data class DeleteNote(val note: Note) : NoteListIntent()
}