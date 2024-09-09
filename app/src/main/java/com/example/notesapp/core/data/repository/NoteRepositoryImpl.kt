package com.example.notesapp.core.data.repository

import com.example.notesapp.core.data.local.NoteDao
import com.example.notesapp.core.data.mapper.toNoteEntityForDelete
import com.example.notesapp.core.data.mapper.toNoteEntityForInsert
import com.example.notesapp.core.data.mapper.toNoteItem
import com.example.notesapp.core.domain.model.Note
import com.example.notesapp.core.domain.repository.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {
    override suspend fun upsertNote(note: Note) =
        noteDao.upsertNoteEntity(note.toNoteEntityForInsert())


    override suspend fun deleteNote(note: Note) =
        noteDao.deleteNoteEntity(note.toNoteEntityForDelete())

    override suspend fun getAllNotes(): List<Note> =
        noteDao.getAllNoteEntities().map { it.toNoteItem() }

    override suspend fun getNotesSortedByTitle(): List<Note> =
        noteDao.getNotesOrderedByTitle().map { it.toNoteItem() }

    override suspend fun getNotesSortedByDateAdded(): List<Note> =
        noteDao.getNotesOrderedByDateAdded().map { it.toNoteItem() }

}