package com.example.notesapp.core.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.notesapp.core.di.AppModule
import com.example.notesapp.core.util.TestTags.ADD_NOTE_FAB
import com.example.notesapp.core.util.TestTags.DELETE_NOTE
import com.example.notesapp.core.util.TestTags.DESCRIPTION_TEXT_FIELD
import com.example.notesapp.core.util.TestTags.NOTES_COUNT_TEXT
import com.example.notesapp.core.util.TestTags.NOTE_IMAGE
import com.example.notesapp.core.util.TestTags.NOTE_IMAGE_CARD
import com.example.notesapp.core.util.TestTags.PICKED_IMAGE
import com.example.notesapp.core.util.TestTags.SAVE_BUTTON
import com.example.notesapp.core.util.TestTags.SEARCH_IMAGE_DIALOG
import com.example.notesapp.core.util.TestTags.SEARCH_IMAGE_TEXT_FIELD
import com.example.notesapp.core.util.TestTags.TITLE_TEXT_FIELD
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class AppEndToEndTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun appEndToEndTest() {
        // Given: The user is on the main screen
        composeRule.onNodeWithTag(ADD_NOTE_FAB)
            .performClick()

        // When: The user enters the title and description for the note
        composeRule.onNodeWithTag(TITLE_TEXT_FIELD)
            .performTextInput("title")
        composeRule.onNodeWithTag(DESCRIPTION_TEXT_FIELD)
            .performTextInput("description")

        // When: The user selects an image for the note
        composeRule.onNodeWithTag(NOTE_IMAGE)
            .performClick()

        // Then: The image search dialog should appear
        composeRule.onNodeWithTag(SEARCH_IMAGE_DIALOG)
            .assertIsDisplayed()

        // When: The user types a search query for the image
        composeRule.onNodeWithTag(SEARCH_IMAGE_TEXT_FIELD)
            .performTextInput("query")

        // Simulate delay for search results to appear (using coroutine for non-blocking behavior)
        runBlocking {
            delay(600) // Wait for the results to load
        }

        // When: The user picks an image from the search results
        composeRule.onNodeWithTag(PICKED_IMAGE + "image3")
            .performClick()

        // Then: The selected image should be displayed in the note
        composeRule.onNodeWithTag(NOTE_IMAGE + "image3")
            .assertIsDisplayed()

        // When: The user saves the note
        composeRule.onNodeWithTag(SAVE_BUTTON)
            .performClick()

        // Then: The note should be visible with the correct content
        composeRule.onNodeWithContentDescription("image3").assertIsDisplayed()
        composeRule.onNodeWithText("title").assertExists()
        composeRule.onNodeWithText("description").assertExists()
        composeRule.onNodeWithTag(NOTES_COUNT_TEXT + "1").assertExists()

        // When: The user deletes the note
        composeRule.onNodeWithContentDescription(DELETE_NOTE + "title").performClick()

        // Then: The note should no longer be displayed
        composeRule.onNodeWithTag(NOTES_COUNT_TEXT + "0").assertExists()
        composeRule.onNodeWithTag(NOTE_IMAGE_CARD + "image3").assertDoesNotExist()
        composeRule.onNodeWithText("title").assertDoesNotExist()
        composeRule.onNodeWithText("description").assertDoesNotExist()
    }


}