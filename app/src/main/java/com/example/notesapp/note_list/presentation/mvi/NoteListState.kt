package com.example.notesapp.note_list.presentation.mvi

import com.example.notesapp.core.domain.model.Note



data class NoteListState(
    val notes: List<Note> = emptyList(),
    val sortOrderByTitle: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)