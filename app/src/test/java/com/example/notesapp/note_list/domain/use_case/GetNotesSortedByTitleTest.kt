package com.example.notesapp.note_list.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notesapp.core.data.repository.FakeNoteRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class GetNotesSortedByTitleTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository

    private lateinit var getNotesSortedByTitle: GetNotesSortedByTitle

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        getNotesSortedByTitle = GetNotesSortedByTitle(fakeNoteRepository)
    }


    @Test
    fun `get notes sort by title, sorted by title`() = runTest {
        //Given
        fakeNoteRepository.shouldHaveFilledList(true)
        //When
        val notes = getNotesSortedByTitle.invoke()
        //Then
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title).isLessThan(notes[i + 1].title)
        }

    }

}
