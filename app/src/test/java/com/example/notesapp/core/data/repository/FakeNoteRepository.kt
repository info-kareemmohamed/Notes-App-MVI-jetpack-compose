package com.example.notesapp.core.data.repository

import com.example.notesapp.core.domain.model.Note
import com.example.notesapp.core.domain.repository.NoteRepository

class FakeNoteRepository : NoteRepository {

    private var notes = mutableListOf<Note>()

    fun shouldHaveFilledList(shouldHaveFilledList: Boolean) {
        notes =
            if (shouldHaveFilledList) {
                mutableListOf(
                    Note("d title 1", "desc 1", "url1", 1),
                    Note("c title 2", "desc 2", "url2", 2),
                    Note("b title 3", "desc 3", "url3", 3),
                    Note("a title 4", "desc 4", "url4", 4)
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

    override suspend fun getAllNotes(): List<Note> {
        return notes
    }
}