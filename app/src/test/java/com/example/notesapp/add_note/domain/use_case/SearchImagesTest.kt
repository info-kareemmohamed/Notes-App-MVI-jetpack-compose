package com.example.notesapp.add_note.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.notesapp.core.data.repository.FakeImagesRepository
import com.example.notesapp.core.util.Resource
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class SearchImagesTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeImagesRepository: FakeImagesRepository
    private lateinit var searchImages: SearchImages

    @Before
    fun setup() {
        fakeImagesRepository = FakeImagesRepository()
        searchImages = SearchImages(fakeImagesRepository)
    }


    @Test
    fun `searching images with valid query returns success with images`() = runTest {
        // Given: The repository does not return an error (successful scenario)
        fakeImagesRepository.setShouldReturnError(false)

        // When: Searching with a valid query
        searchImages("validQuery").test {
            // Then: The first state should indicate loading
            assertTrue(awaitItem() is Resource.Loading)

            // Wait for the result (Success or Error)
            val result = awaitItem()

            // Then: Verify the result is a success and contains valid images
            if (result is Resource.Success) {
                // Ensure the list of images is not null or empty
                assertThat(result.data?.images.isNullOrEmpty()).isFalse()
            } else {
                // Fail the test if the result is an error
                assertFalse(result is Resource.Error)
            }

            // Cancel and ignore any remaining events
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `searching images with invalid query returns error with message`() = runTest {
        // Given: The repository does not return an error (valid setup but invalid query)
        fakeImagesRepository.setShouldReturnError(false)

        // When: Searching with an invalid (empty) query
        searchImages("").test {
            // Then: The first state should indicate loading
            assertTrue(awaitItem() is Resource.Loading)

            // Wait for the result (Success or Error)
            val result = awaitItem()

            // Then: Verify the result is either a success with no images or an error
            if (result is Resource.Success) {
                // Check that the list of images is empty when query is invalid
                assertTrue(result.data?.images.isNullOrEmpty())
            } else {
                // Ensure the result is an error with a valid error message
                assertTrue(result is Resource.Error)
                assertThat(result.message.isNullOrEmpty()).isFalse()
            }

            // Cancel and ignore any remaining events
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `search images with valid query but network error returns error`() = runTest {
        // Given: The repository simulates a network error
        fakeImagesRepository.setShouldReturnError(true)

        // When: Searching with a valid query
        searchImages("query").test {
            // Then: The first emitted state should indicate loading
            assertTrue(awaitItem() is Resource.Loading)

            // Wait for the result (Success or Error)
            val result = awaitItem()

            // Then: Verify that the result is an error
            assertTrue(result is Resource.Error)
            assertThat(result.message.isNullOrEmpty()).isFalse()

            // Cancel and ignore any remaining events
            cancelAndIgnoreRemainingEvents()
        }
    }



}