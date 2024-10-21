package com.gobinda.notepad

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildAt
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
        content = "note content 1",
        lastEditTime = 101L
    )

    private val testNote2 = Note(
        id = 2L,
        content = "note content 2",
        lastEditTime = 102L
    )

    private val testNote3 = Note(
        id = 3L,
        content = "note content 3",
        lastEditTime = 103L
    )

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
        composeRule.onNode(addEditScreenContentInputView).performTextInput(testNote1.content)
        composeRule.onNode(addEditScreenDoneIconBtn).performClick()

        // again verify that we are in NoteListScreen & there is one note
        composeRule.onNode(noteListScreenView).assertIsDisplayed()
        composeRule.onNode(hasText(testNote1.content)).assertIsDisplayed()

        // click on the only note we added & verify that show note screen opened
        composeRule.onNode(hasText(testNote1.content)).performClick()
        composeRule.onNode(showNoteScreenView).assertIsDisplayed()
        composeRule.onNode(hasText(testNote1.content)).assertIsDisplayed()

        // now click on edit note and verify edit note screen opened
        composeRule.onNode(showNoteScreenEditIconBtn).performClick()
        composeRule.onNode(addEditNoteScreenView).assertIsDisplayed()
        composeRule.onNode(hasText(testNote1.content)).assertIsDisplayed()

        // perform some edit and save back by clicking done button
        composeRule.onNode(addEditScreenContentInputView).performTextClearance()
        composeRule.onNode(addEditScreenContentInputView).performTextInput(testNote2.content)
        composeRule.onNode(addEditScreenDoneIconBtn).performClick()

        // confirm that show note screen opened & have the update then press back button
        composeRule.onNode(showNoteScreenView).assertIsDisplayed()
        composeRule.onNode(hasText(testNote2.content)).assertIsDisplayed()
        composeRule.onNode(showNoteScreenBackBtn).performClick()

        // confirm that we are in NoteListScreen and it has the update too
        composeRule.onNode(noteListScreenView).assertIsDisplayed()
        composeRule.onNode(hasText(testNote2.content)).assertIsDisplayed()

        // press on the only note and open show note screen again
        composeRule.onNode(hasText(testNote2.content)).performClick()
        composeRule.onNode(showNoteScreenView).assertIsDisplayed()

        // now click on delete icon button and verify that the dialog open
        composeRule.onNode(showNoteScreenDeleteIconBtn).performClick()
        composeRule.onNode(deleteNoteDialogView).assertIsDisplayed()

        // click yes delete and verify that note list screen open and the list is empty
        composeRule.onNode(deleteDialogYesBtn).performClick()
        composeRule.onNode(noteListScreenView).assertIsDisplayed()
        composeRule.onNode(hasText(testNote2.content)).assertDoesNotExist()
    }

    @Test
    fun addThreeNote_verifyTheirOrderInList_editSecondOne_confirmTheirOrderInList() {
        // all the screen elements declaration
        val noteListScreenView = hasTestTag(TestTag.NoteListScreenView)
        val addEditNoteScreenView = hasTestTag(TestTag.AddEditNoteScreenView)
        val noteListScreenAddIconBtn = hasTestTag(TestTag.NoteListScreenAddIconBtn)
        val noteListScreenLazyColumn = hasTestTag(TestTag.NoteListNonEmptyView_LazyColumn)
        val addEditScreenContentInputView = hasTestTag(TestTag.ContentInputView_InputField)
        val addEditScreenDoneIconBtn = hasTestTag(TestTag.AddEditScreenDoneIconBtn)
        val showNoteScreenEditIconBtn = hasTestTag(TestTag.ShowNoteScreenEditIconBtn)
        val showNoteScreenBackBtn = hasTestTag(TestTag.ShowNoteScreenBackBtn)

        // verifying that we are in NoteListScreen then click add button
        composeRule.onNode(noteListScreenView).assertIsDisplayed()
        composeRule.onNode(noteListScreenAddIconBtn).performClick()

        // verify that we are in add note screen then save a new note
        composeRule.onNode(addEditNoteScreenView).assertIsDisplayed()
        composeRule.onNode(addEditScreenContentInputView).performTextInput(testNote1.content)
        composeRule.onNode(addEditScreenDoneIconBtn).performClick()

        // verify the new note gets added then click add button again to add a new one
        composeRule.onNode(noteListScreenView).assertIsDisplayed()
        composeRule.onNode(hasText(testNote1.content)).assertExists()
        composeRule.onNode(noteListScreenAddIconBtn).performClick()

        // verify that we are in add note screen then save a new note
        composeRule.onNode(addEditNoteScreenView).assertIsDisplayed()
        composeRule.onNode(addEditScreenContentInputView).performTextInput(testNote2.content)
        composeRule.onNode(addEditScreenDoneIconBtn).performClick()

        // verify the list have 2 notes and then click add button again to add a new one
        composeRule.onNode(noteListScreenView).assertIsDisplayed()
        composeRule.onNode(hasText(testNote1.content)).assertExists()
        composeRule.onNode(hasText(testNote2.content)).assertExists()
        composeRule.onNode(noteListScreenAddIconBtn).performClick()

        // verify that we are in add note screen then save a new note
        composeRule.onNode(addEditNoteScreenView).assertIsDisplayed()
        composeRule.onNode(addEditScreenContentInputView).performTextInput(testNote3.content)
        composeRule.onNode(addEditScreenDoneIconBtn).performClick()

        // verify the list have 3 notes and confirm their order
        composeRule.onNode(noteListScreenView).assertIsDisplayed()
        composeRule.onNode(hasText(testNote1.content)).assertExists()
        composeRule.onNode(hasText(testNote2.content)).assertExists()
        composeRule.onNode(hasText(testNote3.content)).assertExists()

        // verifying the order of the notes in the list
        // child positions are not contiguous since we have menu divider as child
        val lazyColumn = composeRule.onNode(noteListScreenLazyColumn)
        lazyColumn.onChildAt(0).assertTextContains(testNote3.content)
        lazyColumn.onChildAt(2).assertTextContains(testNote2.content)
        lazyColumn.onChildAt(4).assertTextContains(testNote1.content)

        // edit the second item by opening add edit note screen
        lazyColumn.onChildAt(2).performClick()
        composeRule.onNode(showNoteScreenBackBtn).assertIsDisplayed()
        composeRule.onNode(showNoteScreenEditIconBtn).performClick()
        composeRule.onNode(addEditNoteScreenView).assertIsDisplayed()

        // perform the edit operation and save back then go back to note list screen
        composeRule.onNode(addEditScreenContentInputView).performTextClearance()
        composeRule.onNode(addEditScreenContentInputView).performTextInput(testNote2.content)
        composeRule.onNode(addEditScreenDoneIconBtn).performClick()
        composeRule.onNode(showNoteScreenBackBtn).performClick()

        // verify the list have 3 notes and confirm their order
        composeRule.onNode(noteListScreenView).assertIsDisplayed()
        composeRule.onNode(hasText(testNote1.content)).assertExists()
        composeRule.onNode(hasText(testNote2.content)).assertExists()
        composeRule.onNode(hasText(testNote3.content)).assertExists()

        // verifying the order of the notes in the list
        // child positions are not contiguous since we have menu divider as child
        lazyColumn.onChildAt(0).assertTextContains(testNote2.content)
        lazyColumn.onChildAt(2).assertTextContains(testNote3.content)
        lazyColumn.onChildAt(4).assertTextContains(testNote1.content)
    }
}