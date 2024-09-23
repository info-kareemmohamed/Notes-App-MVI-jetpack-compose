package com.example.notesapp.add_note.presentation.mvi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.notesapp.MainCoroutineRule
import com.example.notesapp.add_note.domain.use_case.GetNoteById
import com.example.notesapp.add_note.domain.use_case.SearchImages
import com.example.notesapp.add_note.domain.use_case.UpsertNote
import com.example.notesapp.core.data.repository.FakeImagesRepository
import com.example.notesapp.core.data.repository.FakeNoteRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class AddNoteViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    val savedStateHandle = SavedStateHandle().apply {
        set("note_Id",null)
    }

    private lateinit var viewModel: AddNoteViewModel
    private lateinit var upsertNoteUseCase: UpsertNote
    private lateinit var getNoteByIdUseCase: GetNoteById
    private lateinit var searchImages: SearchImages

    private lateinit var fakeImagesRepository: FakeImagesRepository
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setup() {
        fakeImagesRepository = FakeImagesRepository()
        fakeNoteRepository = FakeNoteRepository()
        searchImages = SearchImages(fakeImagesRepository)
        upsertNoteUseCase = UpsertNote(fakeNoteRepository)
        getNoteByIdUseCase = GetNoteById(fakeNoteRepository)
        viewModel =
            AddNoteViewModel(savedStateHandle, upsertNoteUseCase, getNoteByIdUseCase, searchImages)
    }

    @Test
    fun `load note by valid id updates state with note details`() = runTest {
        // Given: The repository contains a list of notes
        fakeNoteRepository.shouldHaveFilledList(true)

        // When: A valid note ID is passed to load the note details
        viewModel.processIntent(AddNoteIntent.LoadNote(1))

        viewModel.state.test {
            // Then: The state should initially indicate loading
            assertThat(awaitItem().isLoading).isTrue()

            // Then: The state should update with the loaded note details and loading should stop
            val loadedState = awaitItem()
            assertThat(loadedState.title).isNotEmpty()
            assertThat(loadedState.isLoading).isFalse()
        }
    }

    @Test
    fun `upsert note with valid data saves note successfully`() = runTest {
        // Given: The repository contains notes
        fakeNoteRepository.shouldHaveFilledList(true)

        // When: A valid note is upserted
        viewModel.processIntent(AddNoteIntent.UpsertNote("title", "desc", "url", 1))

        // Then: The noteSavedFlow should emit true indicating success
        viewModel.noteSavedFlow.test {
            assertThat(awaitItem()).isTrue()
        }
    }

    @Test
    fun `upsert note with invalid data does not save note`() = runTest {
        // Given: The repository contains notes
        fakeNoteRepository.shouldHaveFilledList(true)

        // When: An invalid note (empty title) is upserted
        viewModel.processIntent(AddNoteIntent.UpsertNote("", "desc", "url", 1))

        // Then: The noteSavedFlow should emit false indicating failure
        viewModel.noteSavedFlow.test {
            assertThat(awaitItem()).isFalse()
        }
    }



    @Test
    fun `update description updates state with new description`() = runTest {
        // When: A new description is provided
        viewModel.processIntent(AddNoteIntent.ChangeDescription("new description"))

        // Then: The state should reflect the updated description
        viewModel.state.test {
            assertThat(awaitItem().description).isEqualTo("new description")
        }
    }

    @Test
    fun `update title updates state with new title`() = runTest {
        // When: A new title is provided
        viewModel.processIntent(AddNoteIntent.ChangeTitle("new title"))

        // Then: The state should reflect the updated title
        viewModel.state.test {
            assertThat(awaitItem().title).isEqualTo("new title")
        }
    }

    @Test
    fun `update image URL updates state with new image URL`() = runTest {
        // When: A new image URL is provided
        viewModel.processIntent(AddNoteIntent.ChangeImageUrl("new image url"))

        // Then: The state should reflect the updated image URL
        viewModel.state.test {
            assertThat(awaitItem().imageUrl).isEqualTo("new image url")
        }
    }

    @Test
    fun `toggle images dialog visibility updates state correctly`() = runTest {
        // Given: The images dialog is initially not showing
        assertThat(viewModel.state.value.isImagesDialogShowing).isFalse()

        // When: The visibility of the images dialog is toggled
        viewModel.processIntent(AddNoteIntent.UpdateImagesDialogVisibility)

        // Then: The state should reflect that the images dialog is now showing
        viewModel.state.test {
            assertThat(awaitItem().isImagesDialogShowing).isTrue()
        }
    }



    @Test
    fun `search images with valid query returns images`() = runTest {
        // Given: The repository is set to return images
        fakeImagesRepository.setShouldReturnError(false)

        // When: A valid query is entered for searching images
        viewModel.processIntent(AddNoteIntent.UpdateSearchImageQuery("query"))

        // Then: The state should reflect the search process and results
        viewModel.state.test {
            //Check update search images query
            assertThat(awaitItem().searchImagesQuery).isEqualTo("query")

            // Check initial loading state
            val loadingState = awaitItem()
            assertThat(loadingState.isLoadingImagesDialog).isTrue()

            // Check the state after loading completes
            val loadedState = awaitItem()
            assertThat(loadedState.isLoadingImagesDialog).isFalse()
            assertThat(loadedState.imageList).isNotEmpty()
            assertThat(loadedState.searchImagesError).isNull()
        }
    }

    @Test
    fun `search images with invalid query returns error`() = runTest {
        // Given: The repository is configured to simulate an error when searching for images
        fakeImagesRepository.setShouldReturnError(true)

        // When: An empty query is entered for searching images
        viewModel.processIntent(AddNoteIntent.UpdateSearchImageQuery(""))

        // Then: Verify the state updates correctly
        viewModel.state.test {
            // Check that the search images query is updated to an empty string
            assertThat(awaitItem().searchImagesQuery).isEqualTo("")

            // Check initial loading state is true
            val loadingState = awaitItem()
            assertThat(loadingState.isLoadingImagesDialog).isTrue()

            // Check the state after loading completes
            val loadedState = awaitItem()
            assertThat(loadedState.isLoadingImagesDialog).isFalse()
            assertThat(loadedState.imageList).isEmpty()
            assertThat(loadedState.searchImagesError).isNotNull()
        }
    }



}