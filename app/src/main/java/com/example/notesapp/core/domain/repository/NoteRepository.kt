package com.example.notesapp.core.domain.repository

import com.example.notesapp.core.domain.model.Note

interface NoteRepository {

    suspend fun upsertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun getAllNotes(): List<Note>
}