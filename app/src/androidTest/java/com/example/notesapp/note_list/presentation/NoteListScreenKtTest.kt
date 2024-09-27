package com.example.notesapp.note_list.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.notesapp.core.di.AppModule
import com.example.notesapp.core.presentation.MainActivity
import com.example.notesapp.core.util.TestTags.ADD_NOTE_FAB
import com.example.notesapp.core.util.TestTags.DELETE_NOTE
import com.example.notesapp.core.util.TestTags.DESCRIPTION_TEXT_FIELD
import com.example.notesapp.core.util.TestTags.NOTES_COUNT_TEXT
import com.example.notesapp.core.util.TestTags.SAVE_BUTTON
import com.example.notesapp.core.util.TestTags.TITLE_TEXT_FIELD
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NoteListScreenKtTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }


    private fun insertNote(title: String = "title", description: String = "description") {
        composeTestRule.onNodeWithTag(ADD_NOTE_FAB).performClick()
        composeTestRule.onNodeWithTag(TITLE_TEXT_FIELD).performTextInput(title)
        composeTestRule.onNodeWithTag(DESCRIPTION_TEXT_FIELD).performTextInput(description)
        composeTestRule.onNodeWithTag(SAVE_BUTTON).performClick()
    }


    @Test
    fun insertNote_noteIsDisplayedAndCountIsCorrect() {
        // Given: A new note is inserted with title "title" and description "description"
        insertNote()

        // When: The note is added successfully
        // Then: The note with the specified title and description should be displayed
        composeTestRule.onNodeWithText("title").assertExists()
        composeTestRule.onNodeWithText("description").assertExists()

        // Then: The note count should increase to 1
        composeTestRule.onNodeWithTag(NOTES_COUNT_TEXT + "1").assertExists()
    }

    @Test
    fun deleteNote_noteIsNotDisplayedAndCountIsCorrect() {
        // Given: A new note is inserted with title "title" and description "description"
        insertNote()

        // When: The note with title "title" is deleted
        composeTestRule.onNodeWithContentDescription(DELETE_NOTE + "title").performClick()

        // Then: The note with the specified title and description should no longer exist
        composeTestRule.onNodeWithText("title").assertDoesNotExist()
        composeTestRule.onNodeWithText("description").assertDoesNotExist()

        // Then: The note count should decrease to 0
        composeTestRule.onNodeWithTag(NOTES_COUNT_TEXT + "0").assertExists()
    }

}