package com.example.notesapp.core.data.repository

import com.example.notesapp.core.domain.model.Note
import com.example.notesapp.core.domain.repository.NoteRepository

class FakeNoteRepository : NoteRepository {

    private var notes = mutableListOf<Note>()

    fun shouldHaveFilledList(shouldHaveFilledList: Boolean) {
        notes =
            if (shouldHaveFilledList) {
                mutableListOf(
                    Note("a title 1", "desc 1", "url1", 4,1),
                    Note("b title 2", "desc 2", "url2", 3,2),
                    Note("c title 3", "desc 3", "url3", 2,3),
                    Note("d title 4", "desc 4", "url4", 1,4)
                )
            } else {
                mutableListOf()
            }
    }



    override suspend fun upsertNote(note: Note) {
        notes.add(note)
    }

    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
    }

    override suspend fun getNoteById(id: Int): Note? = notes.find { it.id == id }

    override suspend fun getAllNotes(): List<Note> =notes

    override suspend fun getNotesSortedByTitle(): List<Note> = notes.sortedBy { it.title }

    override suspend fun getNotesSortedByDateAdded(): List<Note> = notes.sortedBy { it.dateAdded }
}