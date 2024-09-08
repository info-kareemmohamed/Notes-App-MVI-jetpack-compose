package com.example.notesapp.core.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import com.example.notesapp.core.di.AppModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NoteDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var noteDb: NoteDb

    private lateinit var noteDao: NoteDao


    @Before
    fun setup() {
        hiltRule.inject()
        noteDao = noteDb.noteDao
    }

    @After
    fun tearDown() {
        noteDb.close()
    }

    @Test
    fun getAllNotes_returnsEmptyList() = runTest {
        //When
        val notes = noteDao.getAllNoteEntities()
        //Then
        assert(notes.isEmpty())
    }

    @Test
    fun insertNote_NoteIsInserted() = runTest {
        //Given
        val note = NoteEntity("title", "description", "imageUrl", System.currentTimeMillis(), 1)

        //When
        noteDao.upsertNoteEntity(note)
        val notes = noteDao.getAllNoteEntities()

        //Then
        assert(notes.contains(note))
    }

    @Test
    fun deleteNote_NoteIsDeleted() = runTest {
        //Given
        val note = NoteEntity("title", "description", "imageUrl", System.currentTimeMillis(), 1)
        noteDao.upsertNoteEntity(note)

        //When
        noteDao.deleteNoteEntity(note)
        val notes = noteDao.getAllNoteEntities()
        //Then
        assert(!notes.contains(note))
    }

    @Test
    fun updateNote_NoteIsUpdated() = runTest {
        //Given
        val note = NoteEntity("title", "description", "imageUrl", System.currentTimeMillis(), 1)
        noteDao.upsertNoteEntity(note)
        val updatedNote = NoteEntity(
            "updatedTitle",
            "updatedDescription",
            "updatedImageUrl",
            System.currentTimeMillis(),
            1
        )

        //When
        noteDao.upsertNoteEntity(updatedNote)
        val notes = noteDao.getAllNoteEntities()

        //Then
        assert(notes.contains(updatedNote))
    }

    @Test
    fun getNotesSortedByDateAdded() = runTest {
        //Given
        val note1 = NoteEntity("title1", "description1", "imageUrl1", 3, 1)
        val note2 = NoteEntity("title2", "description2", "imageUrl2", 2, 2)

        //When
        noteDao.upsertNoteEntity(note1)
        noteDao.upsertNoteEntity(note2)
        val notes = noteDao.getNotesOrderedByDateAdded()

        //Then
        assert(notes[0] == note2)
        assert(notes[1] == note1)

    }



    @Test
    fun getNotesSortedByTitle() = runTest {
        //Given
        val note1 = NoteEntity("B", "description1", "imageUrl1", 3, 1)
        val note2 = NoteEntity("A", "description2", "imageUrl2", 2, 2)
        val note3 = NoteEntity("C", "description2", "imageUrl2", 5, 3)

        //When
        noteDao.upsertNoteEntity(note1)
        noteDao.upsertNoteEntity(note2)
        noteDao.upsertNoteEntity(note3)
        val notes = noteDao.getNotesOrderedByTitle()

        //Then
        assert(notes[0] == note2)
        assert(notes[1] == note1)
        assert(notes[2] == note3)

    }



}