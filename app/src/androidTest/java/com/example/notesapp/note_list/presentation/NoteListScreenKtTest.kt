package com.example.notesapp.note_list.presentation

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


    @Test
    fun insertNote_noteIsDisplayed() {
        composeTestRule.onNodeWithTag(ADD_NOTE_FAB).performClick()
        composeTestRule.onNodeWithTag(TITLE_TEXT_FIELD).performTextInput("title")
        composeTestRule.onNodeWithTag(DESCRIPTION_TEXT_FIELD).performTextInput("description")
        composeTestRule.onNodeWithTag(SAVE_BUTTON).performClick()


        composeTestRule.onNodeWithText("title").assertExists()
        composeTestRule.onNodeWithText("description").assertExists()


    }

    @Test
    fun deleteNote_noteIsNotDisplayed() {
        composeTestRule.onNodeWithTag(ADD_NOTE_FAB).performClick()
        composeTestRule.onNodeWithTag(TITLE_TEXT_FIELD).performTextInput("title")
        composeTestRule.onNodeWithTag(DESCRIPTION_TEXT_FIELD).performTextInput("description")
        composeTestRule.onNodeWithTag(SAVE_BUTTON).performClick()
        composeTestRule.onNodeWithContentDescription(DELETE_NOTE + "title").performClick()

        composeTestRule.onNodeWithText("title").assertDoesNotExist()
        composeTestRule.onNodeWithText("description").assertDoesNotExist()


    }

}