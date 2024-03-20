package com.gobinda.notepad.ui.screens.noteList

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.gobinda.notepad.R
import com.gobinda.notepad.ui.screens.common.TestTag
import com.gobinda.notepad.ui.theme.NotepadTheme
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test

class NoteListEmptyViewTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun testingView_whenMainText_shouldBeEqualToInputText() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val textFromResource = context.resources.getString(R.string.text_click_to_add_new_note)

        composeRule.setContent {
            NotepadTheme {
                NoteListEmptyView { }
            }
        }

        val mainTextView = hasTestTag(TestTag.NoteListEmptyView_Main_Text)
        composeRule.onNode(mainTextView).assertTextEquals(textFromResource)
    }

    @Test
    fun testingView_whenMainTextClicked_callBackShouldBeInvoked() {
        val counter = mutableIntStateOf(0)
        composeRule.setContent {
            NotepadTheme {
                NoteListEmptyView { counter.intValue++ }
            }
        }

        val mainTextView = hasTestTag(TestTag.NoteListEmptyView_Main_Text)
        TestCase.assertEquals(0, counter.intValue)

        composeRule.onNode(mainTextView).performClick()
        composeRule.onNode(mainTextView).performClick()
        composeRule.onNode(mainTextView).performClick()
        TestCase.assertEquals(3, counter.intValue)

        composeRule.onNode(mainTextView).performClick()
        composeRule.onNode(mainTextView).performClick()
        TestCase.assertEquals(5, counter.intValue)
    }
}