package com.example.notesapp.note_list.presentation.mvi

import com.example.notesapp.core.domain.model.Note

sealed class NoteListIntent {
    data class SortNotesByTitle(val sort: Boolean) : NoteListIntent()
    data class DeleteNote(val note: Note) : NoteListIntent()
}