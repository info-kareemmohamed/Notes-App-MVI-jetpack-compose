package com.example.notesapp.add_note.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notesapp.core.data.repository.FakeNoteRepository
import com.example.notesapp.core.domain.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class GetNoteByIdTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var getNoteById: GetNoteById

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        getNoteById = GetNoteById(fakeNoteRepository)
    }

    @Test
    fun `get note by id, return note`() = runTest {
        //Given
        fakeNoteRepository.shouldHaveFilledList(true)
        val note = Note("b title 2", "desc 2", "url2", 2, 2)

        //When
        val noteById = getNoteById(2)

        println(noteById)
        //Then
        assertThat(noteById).isEqualTo(note)
    }

    @Test
    fun `get note by wrong id, return null`() = runTest {
        //Given
        fakeNoteRepository.shouldHaveFilledList(true)

        //When
        val noteById = getNoteById(5)

        //Then
        assertThat(noteById).isNull()
    }


}