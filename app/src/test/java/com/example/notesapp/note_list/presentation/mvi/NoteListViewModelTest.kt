package com.example.notesapp.note_list.presentation.mvi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.notesapp.core.data.repository.FakeNoteRepository
import com.example.notesapp.core.domain.model.Note
import com.example.notesapp.note_list.domain.use_case.DeleteNote
import com.example.notesapp.note_list.domain.use_case.GetNotesSortedByDateAdded
import com.example.notesapp.note_list.domain.use_case.GetNotesSortedByTitle
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class NoteListViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var fakeNoteRepository: FakeNoteRepository

    private lateinit var deleteNoteUseCase: DeleteNote
    private lateinit var getNotesSortedByTitle: GetNotesSortedByTitle
    private lateinit var getNotesSortedByDate: GetNotesSortedByDateAdded
    private lateinit var viewModel: NoteListViewModel

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        deleteNoteUseCase = DeleteNote(fakeNoteRepository)
        getNotesSortedByTitle = GetNotesSortedByTitle(fakeNoteRepository)
        getNotesSortedByDate = GetNotesSortedByDateAdded(fakeNoteRepository)
        viewModel =
            NoteListViewModel(deleteNoteUseCase, getNotesSortedByTitle, getNotesSortedByDate)
    }


    @Test
    fun `get notes sort by title, sorted by title and loading state is correct , sort order is correct`() =
        runTest {
            // Given
            fakeNoteRepository.shouldHaveFilledList(true)

            viewModel.state.test {
                // When
                // Skip initial state
                awaitItem()
                viewModel.processIntent(NoteListIntent.SortNotesByTitle(true))


                // Then
                assertThat(awaitItem().isLoading).isTrue()
                val updatedState = awaitItem()

                assertThat(updatedState.isLoading).isFalse()
                assertThat(updatedState.sortOrderByTitle).isTrue()
                for (i in 0..updatedState.notes.size - 2) {
                    assertThat(updatedState.notes[i].title).isLessThan(updatedState.notes[i + 1].title)
                }
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `sort notes by date added, notes are sorted by date and loading state is handled correctly, sort order is false`() =
        runTest {

            // Given
            fakeNoteRepository.shouldHaveFilledList(true)

            viewModel.state.test {
                // When
                // Skip initial state
                awaitItem()
                viewModel.processIntent(NoteListIntent.SortNotesByTitle(false))


                // Then
                assertThat(awaitItem().isLoading).isTrue()
                val updatedState = awaitItem()

                assertThat(updatedState.isLoading).isFalse()
                assertThat(updatedState.sortOrderByTitle).isFalse()
                for (i in 0..updatedState.notes.size - 2) {
                    assertThat(updatedState.notes[i].dateAdded).isLessThan(updatedState.notes[i + 1].dateAdded)
                }
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `delete note, note is deleted and loading state is correct`() = runTest {
        // Given
        fakeNoteRepository.shouldHaveFilledList(true)
        val note = Note("b title 2", "desc 2", "url2", 3, 2)

        viewModel.state.test {

            // When
            // Skip initial state
            awaitItem()
            viewModel.processIntent(NoteListIntent.DeleteNote(note))


            // Then
            assertThat(awaitItem().isLoading).isTrue()

            // Wait for the state to update after the deletion
            val updatedState = awaitItem()

            assertThat(updatedState.notes.contains(note)).isFalse() // Ensure the note is deleted
            assertThat(updatedState.isLoading).isFalse() // Ensure loading state is false

            cancelAndIgnoreRemainingEvents()
        }
    }


}