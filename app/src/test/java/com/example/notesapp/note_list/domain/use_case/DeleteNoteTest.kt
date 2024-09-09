package com.example.notesapp.note_list.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notesapp.core.data.repository.FakeNoteRepository
import com.example.notesapp.core.domain.model.Note
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.google.common.truth.Truth.assertThat


class DeleteNoteTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var deleteNote: DeleteNote

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        deleteNote = DeleteNote(fakeNoteRepository)
        fakeNoteRepository.shouldHaveFilledList(true)
    }

    @Test
    fun `delete note from list, note is deleted`() = runTest {
        // Given
        val note = Note("b title 2", "desc 2", "url2", 2)

        //When
        deleteNote.invoke(note)

        //Then
        assertThat(fakeNoteRepository.getAllNotes().contains(note)).isFalse()
    }


}