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

class TitleInputViewTest {
    
    companion object {
        private const val HINT_TEXT = "this is a hint text"
        private const val MAIN_TEXT = "the is a test text"
    }

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun titleInputView_showHintText_whenMainTextIsEmpty() {
        composeRule.setContent {
            TitleInputView(
                text = "",
                hintText = HINT_TEXT,
                onValueChanged = { }
            )
        }

        val hintText = hasTestTag(TestTag.TitleInputView_HintText)
        composeRule.onNode(hintText).assertIsDisplayed()
        composeRule.onNode(hintText).assertTextEquals(HINT_TEXT)
    }

    @Test
    fun titleInputView_hideHintText_whenMainTextIsNotEmpty() {
        composeRule.setContent {
            TitleInputView(
                text = MAIN_TEXT,
                hintText = HINT_TEXT,
                onValueChanged = { }
            )
        }

        val hintTextView = hasTestTag(TestTag.TitleInputView_HintText)
        val mainTextView = hasTestTag(TestTag.TitleInputView_InputField)

        composeRule.onNode(hintTextView).assertDoesNotExist()
        composeRule.onNode(mainTextView).assertIsDisplayed()
        composeRule.onNode(mainTextView).assertTextEquals(MAIN_TEXT)
    }

    @Test
    fun titleInputView_updateMainText_whenWriteSomething() {
        val textInputData = mutableStateOf("")
        composeRule.setContent {
            TitleInputView(
                text = textInputData.value,
                hintText = HINT_TEXT,
                onValueChanged = { textInputData.value = it }
            )
        }

        val hintTextView = hasTestTag(TestTag.TitleInputView_HintText)
        val inputField = hasTestTag(TestTag.TitleInputView_InputField)

        // initially hint will be visible
        composeRule.onNode(hintTextView).assertIsDisplayed()
        composeRule.onNode(hintTextView).assertTextEquals(HINT_TEXT)
        composeRule.onNode(inputField).assertTextEquals("")

        // writing some text in the input field
        composeRule.onNode(inputField).performTextInput("new")

        // now hint will be invisible & input field will be visible
        composeRule.onNode(hintTextView).assertDoesNotExist()
        composeRule.onNode(inputField).assertIsDisplayed()
        composeRule.onNode(inputField).assertTextEquals("new")

        // again writing some text in the input field
        composeRule.onNode(inputField).performTextInput(" title")

        // so hint will be invisible & input field will be visible
        composeRule.onNode(hintTextView).assertDoesNotExist()
        composeRule.onNode(inputField).assertIsDisplayed()
        composeRule.onNode(inputField).assertTextEquals("new title")

        // now clear text from the input field
        composeRule.onNode(inputField).performTextClearance()

        // so again - hint will be visible
        composeRule.onNode(hintTextView).assertIsDisplayed()
        composeRule.onNode(hintTextView).assertTextEquals(HINT_TEXT)
        composeRule.onNode(inputField).assertTextEquals("")
    }
}