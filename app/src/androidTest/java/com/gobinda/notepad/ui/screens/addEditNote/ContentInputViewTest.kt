package com.gobinda.notepad.ui.screens.addEditNote

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import com.gobinda.notepad.ui.screens.common.TestTag
import org.junit.Rule
import org.junit.Test

class ContentInputViewTest {

    companion object {
        private const val HINT_TEXT = "this is a hint text"
        private const val MAIN_TEXT = "the is a test text"
    }

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun contentInputView_showHintText_whenMainTextIsEmpty() {
        composeRule.setContent {
            ContentInputView(
                text = "",
                hintText = HINT_TEXT,
                onValueChanged = { }
            )
        }

        val hintText = hasTestTag(TestTag.ContentInputView_HintText)
        composeRule.onNode(hintText).assertIsDisplayed()
        composeRule.onNode(hintText).assertTextEquals(HINT_TEXT)
    }

    @Test
    fun contentInputView_hideHintText_whenMainTextIsNotEmpty() {
        composeRule.setContent {
            ContentInputView(
                text = MAIN_TEXT,
                hintText = HINT_TEXT,
                onValueChanged = { }
            )
        }

        val hintTextView = hasTestTag(TestTag.ContentInputView_HintText)
        val mainTextView = hasTestTag(TestTag.ContentInputView_InputField)

        composeRule.onNode(hintTextView).assertDoesNotExist()
        composeRule.onNode(mainTextView).assertIsDisplayed()
        composeRule.onNode(mainTextView).assertTextEquals(MAIN_TEXT)
    }

    @Test
    fun contentInputView_updateMainText_whenWriteSomething() {
        val textInputData = mutableStateOf("")
        composeRule.setContent {
            ContentInputView(
                text = textInputData.value,
                hintText = HINT_TEXT,
                onValueChanged = { textInputData.value = it }
            )
        }

        val hintTextView = hasTestTag(TestTag.ContentInputView_HintText)
        val inputField = hasTestTag(TestTag.ContentInputView_InputField)

        // initially hint will be visible
        composeRule.onNode(hintTextView).assertIsDisplayed()
        composeRule.onNode(hintTextView).assertTextEquals(HINT_TEXT)
        composeRule.onNode(inputField).assertTextEquals("")

        // writing some text in the input field
        composeRule.onNode(inputField).performTextInput("new text")

        // now hint will be invisible & input field will be visible
        composeRule.onNode(hintTextView).assertDoesNotExist()
        composeRule.onNode(inputField).assertIsDisplayed()
        composeRule.onNode(inputField).assertTextEquals("new text")

        // again writing some text in the input field
        composeRule.onNode(inputField).performTextInput(" done")

        // so hint will be invisible & input field will be visible
        composeRule.onNode(hintTextView).assertDoesNotExist()
        composeRule.onNode(inputField).assertIsDisplayed()
        composeRule.onNode(inputField).assertTextEquals("new text done")

        // now clear text from the input field
        composeRule.onNode(inputField).performTextClearance()

        // so again - hint will be visible
        composeRule.onNode(hintTextView).assertIsDisplayed()
        composeRule.onNode(hintTextView).assertTextEquals(HINT_TEXT)
        composeRule.onNode(inputField).assertTextEquals("")
    }
}