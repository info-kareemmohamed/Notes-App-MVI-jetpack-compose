package com.example.notesapp.add_note.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notesapp.core.data.repository.FakeNoteRepository
import com.example.notesapp.note_list.domain.use_case.GetAllNotes
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class UpsertNoteTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var upsertNote: UpsertNote


    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        upsertNote = UpsertNote(fakeNoteRepository)
    }


    @Test
    fun `upsert note with empty title, return false`() = runTest {
        //Given
        val isInserted = upsertNote.invoke("", "Hi", "aa", 1)

        //Then
        assertThat(isInserted).isFalse()


    }

    @Test
    fun `upsert note with empty description, return false`() = runTest {
        //Given
        val isInserted = upsertNote.invoke("title", "", "aa", 1)

        //Then
        assertThat(isInserted).isFalse()
    }

    @Test
    fun `upsert a valid note, return true`() = runTest {
        //Given
        val isInserted = upsertNote.invoke("title", "Hi", "aa", 1)

        //Then
        assertThat(isInserted).isTrue()
    }


}