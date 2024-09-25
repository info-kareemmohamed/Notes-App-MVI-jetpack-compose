package com.example.notesapp.core.domain.repository

import com.example.notesapp.core.domain.model.Note

interface NoteRepository {

    suspend fun upsertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun getNoteById(id: Int): Note?

    suspend fun getAllNotes(): List<Note>

    suspend fun getNotesSortedByTitle(): List<Note>

    suspend fun getNotesSortedByDateAdded(): List<Note>


}