package com.gobinda.notepad.ui.screens.noteList

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import com.gobinda.notepad.domain.model.NoteAsListItem
import com.gobinda.notepad.ui.theme.NotepadTheme
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test

class NoteListNonEmptyValueTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun noteListTest_showingFiveNote_shouldBeClickable() {
        val testNoteItems = listOf(
            NoteAsListItem("note title 1", 1),
            NoteAsListItem("note title 2", 2),
            NoteAsListItem("note title 3", 3),
            NoteAsListItem("note title 4", 4),
            NoteAsListItem("note title 5", 5)
        )

        var lastSelectedNoteId = -1L

        composeRule.setContent {
            NotepadTheme {
                NoteListNonEmptyValue(
                    noteItems = testNoteItems,
                    onItemClicked = { lastSelectedNoteId = it }
                )
            }
        }

        testNoteItems.forEach { currentNoteData ->
            val currentView = hasText(currentNoteData.title)
            composeRule.onNode(currentView).assertExists()
            composeRule.onNode(currentView).assertIsDisplayed()
            composeRule.onNode(currentView).performClick()
            TestCase.assertEquals(lastSelectedNoteId, currentNoteData.id)
        }
    }

}