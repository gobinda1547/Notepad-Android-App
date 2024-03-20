package com.gobinda.notepad.ui.screens.showNote

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.gobinda.notepad.R
import com.gobinda.notepad.ui.theme.NotepadTheme
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test

class DeleteNoteDialogTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun deleteNoteDialog_verifyingAllTheTexts() {
        composeRule.setContent {
            NotepadTheme {
                DeleteNoteDialog(onConfirm = { /*TODO*/ }) {

                }
            }
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val titleText = context.resources.getString(R.string.delete_dialog_title)
        val contentText = context.resources.getString(R.string.delete_dialog_content)
        val yesBtnText = context.resources.getString(R.string.delete_dialog_positive_button_text)
        val noBtnText = context.resources.getString(R.string.delete_dialog_negative_button_text)

        composeRule.onNode(hasText(titleText)).assertExists()
        composeRule.onNode(hasText(contentText)).assertExists()
        composeRule.onNode(hasText(yesBtnText)).assertExists()
        composeRule.onNode(hasText(noBtnText)).assertExists()
    }

    @Test
    fun deleteNoteDialog_positiveBtnClicked_callbackShouldBeInvoked() {
        var isConfirmed = false
        var isDismissed = false

        composeRule.setContent {
            NotepadTheme {
                DeleteNoteDialog(
                    onConfirm = { isConfirmed = true },
                    onDismiss = { isDismissed = true }
                )
            }
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val yesBtnText = context.resources.getString(R.string.delete_dialog_positive_button_text)
        composeRule.onNode(hasText(yesBtnText)).assertExists().performClick()
        TestCase.assertEquals(true, isConfirmed)
        TestCase.assertEquals(false, isDismissed)
    }

    @Test
    fun deleteNoteDialog_negativeBtnClicked_callbackShouldBeInvoked() {
        var isConfirmed = false
        var isDismissed = false

        composeRule.setContent {
            NotepadTheme {
                DeleteNoteDialog(
                    onConfirm = { isConfirmed = true },
                    onDismiss = { isDismissed = true }
                )
            }
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val noBtnText = context.resources.getString(R.string.delete_dialog_negative_button_text)
        composeRule.onNode(hasText(noBtnText)).assertExists().performClick()
        TestCase.assertEquals(false, isConfirmed)
        TestCase.assertEquals(true, isDismissed)
    }
}