package com.gobinda.notepad

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import com.gobinda.notepad.domain.model.Note
import com.gobinda.notepad.ui.screens.common.TestTag
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class EndToEndTest {

    private val testNote1 = Note(
        id = 1L,
        title = "note title 1",
        content = "note content 1",
        lastEditTime = 101L
    )

    private val testNote2 = Note(
        id = 2L,
        title = "note title 2",
        content = "note content 2",
        lastEditTime = 102L
    )

    private val testNote3 = Note(
        id = 3L,
        title = "note title 3",
        content = "note content 3",
        lastEditTime = 103L
    )

    private val testNoteList = listOf(testNote1, testNote2, testNote3)

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun useAppContext() {
        hiltRule.inject()
    }

    @Test
    fun addOneNoteAndConfirm_thenEditAndConfirm_thenDeleteAndConfirm() {
        // all the screen elements declaration
        val noteListScreenView = hasTestTag(TestTag.NoteListScreenView)
        val showNoteScreenView = hasTestTag(TestTag.ShowNoteScreenView)
        val addEditNoteScreenView = hasTestTag(TestTag.AddEditNoteScreenView)
        val deleteNoteDialogView = hasTestTag(TestTag.DeleteNoteDialogView)
        val noteListScreenAddIconBtn = hasTestTag(TestTag.NoteListScreenAddIconBtn)
        val addEditScreenTitleInputView = hasTestTag(TestTag.TitleInputView_InputField)
        val addEditScreenContentInputView = hasTestTag(TestTag.ContentInputView_InputField)
        val addEditScreenDoneIconBtn = hasTestTag(TestTag.AddEditScreenDoneIconBtn)
        val showNoteScreenEditIconBtn = hasTestTag(TestTag.ShowNoteScreenEditIconBtn)
        val showNoteScreenDeleteIconBtn = hasTestTag(TestTag.ShowNoteScreenDeleteIconBtn)
        val showNoteScreenBackBtn = hasTestTag(TestTag.ShowNoteScreenBackBtn)
        val deleteDialogYesBtn = hasTestTag(TestTag.DeleteNoteDialogYesBtn)

        // verifying that we are in NoteListScreen then click add button
        composeRule.onNode(noteListScreenView).assertIsDisplayed()
        composeRule.onNode(noteListScreenAddIconBtn).performClick()

        // verify that we are in add note screen then save a new note
        composeRule.onNode(addEditNoteScreenView).assertIsDisplayed()
        composeRule.onNode(addEditScreenTitleInputView).performTextInput(testNote1.title)
        composeRule.onNode(addEditScreenContentInputView).performTextInput(testNote1.content)
        composeRule.onNode(addEditScreenDoneIconBtn).performClick()

        // again verify that we are in NoteListScreen & there is one note
        composeRule.onNode(noteListScreenView).assertIsDisplayed()
        composeRule.onNode(hasText(testNote1.title)).assertIsDisplayed()

        // click on the only note we added & verify that show note screen opened
        composeRule.onNode(hasText(testNote1.title)).performClick()
        composeRule.onNode(showNoteScreenView).assertIsDisplayed()
        composeRule.onNode(hasText(testNote1.title)).assertIsDisplayed()
        composeRule.onNode(hasText(testNote1.content)).assertIsDisplayed()

        // now click on edit note and verify edit note screen opened
        composeRule.onNode(showNoteScreenEditIconBtn).performClick()
        composeRule.onNode(addEditNoteScreenView).assertIsDisplayed()
        composeRule.onNode(hasText(testNote1.title)).assertIsDisplayed()
        composeRule.onNode(hasText(testNote1.content)).assertIsDisplayed()

        // perform some edit and save back by clicking done button
        composeRule.onNode(addEditScreenTitleInputView).performTextClearance()
        composeRule.onNode(addEditScreenContentInputView).performTextClearance()
        composeRule.onNode(addEditScreenTitleInputView).performTextInput(testNote2.title)
        composeRule.onNode(addEditScreenContentInputView).performTextInput(testNote2.content)
        composeRule.onNode(addEditScreenDoneIconBtn).performClick()

        // confirm that show note screen opened & have the update then press back button
        composeRule.onNode(showNoteScreenView).assertIsDisplayed()
        composeRule.onNode(hasText(testNote2.title)).assertIsDisplayed()
        composeRule.onNode(hasText(testNote2.content)).assertIsDisplayed()
        composeRule.onNode(showNoteScreenBackBtn).performClick()

        // confirm that we are in NoteListScreen and it has the update too
        composeRule.onNode(noteListScreenView).assertIsDisplayed()
        composeRule.onNode(hasText(testNote2.title)).assertIsDisplayed()

        // press on the only note and open show note screen again
        composeRule.onNode(hasText(testNote2.title)).performClick()
        composeRule.onNode(showNoteScreenView).assertIsDisplayed()

        // now click on delete icon button and verify that the dialog open
        composeRule.onNode(showNoteScreenDeleteIconBtn).performClick()
        composeRule.onNode(deleteNoteDialogView).assertIsDisplayed()

        // click yes delete and verify that note list screen open and the list is empty
        composeRule.onNode(deleteDialogYesBtn).performClick()
        composeRule.onNode(noteListScreenView).assertIsDisplayed()
        composeRule.onNode(hasText(testNote2.title)).assertDoesNotExist()
    }
}