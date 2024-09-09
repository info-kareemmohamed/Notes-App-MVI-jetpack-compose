package com.example.notesapp.note_list.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notesapp.core.data.repository.FakeNoteRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class GetAllNotesTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var getAllNotes: GetAllNotes

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        getAllNotes = GetAllNotes(fakeNoteRepository)

    }


    @Test
    fun `get notes from empty list, empty list`() = runTest {
        //Given
        fakeNoteRepository.shouldHaveFilledList(false)

        //When
        val notes = getAllNotes.invoke()

        //Then
        assertThat(notes.isEmpty())
    }

    @Test
    fun `get all notes `() = runTest {
        //Given
        fakeNoteRepository.shouldHaveFilledList(true)

        //When
        val notes = getAllNotes.invoke()

        //Then
        assertThat(notes.size).isEqualTo(4)

    }


}