package com.example.notesapp.core.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface NoteDao {

    @Upsert
    suspend fun upsertNoteEntity(note: NoteEntity)

    @Query("SELECT * FROM note_entity")
    suspend fun getAllNoteEntities(): List<NoteEntity>

   @Query("SELECT * FROM note_entity WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteEntity?

    @Delete
    suspend fun deleteNoteEntity(note: NoteEntity)

    @Query("SELECT * FROM note_entity ORDER BY title ASC")
    suspend fun getNotesOrderedByTitle(): List<NoteEntity>

    @Query("SELECT * FROM note_entity ORDER BY dateAdded ASC")
    suspend fun getNotesOrderedByDateAdded(): List<NoteEntity>
}