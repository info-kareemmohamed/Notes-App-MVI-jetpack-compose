package com.example.notesapp.note_list.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notesapp.core.data.repository.FakeNoteRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class GetNotesSortedByDateAddedTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository

    private lateinit var getNotesSortedByDateAdded: GetNotesSortedByDateAdded

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        getNotesSortedByDateAdded = GetNotesSortedByDateAdded(fakeNoteRepository)
    }

    @Test
    fun `get notes sort by date added, sorted by date added`() = runTest {
        //Given
        fakeNoteRepository.shouldHaveFilledList(true)
        //When
        val notes = getNotesSortedByDateAdded.invoke()
        //Then
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].dateAdded).isLessThan(notes[i + 1].dateAdded)
        }

    }


}