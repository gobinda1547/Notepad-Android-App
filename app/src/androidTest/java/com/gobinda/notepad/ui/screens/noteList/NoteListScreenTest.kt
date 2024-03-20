package com.gobinda.notepad.ui.screens.noteList

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasNoClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.testing.TestNavHostController
import com.gobinda.notepad.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NoteListScreenTest {

    /**
     * We need hilt rule cause the view model that [NoteListScreen] is using
     * will be automatically created by hilt.
     */
    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composableRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composableRule.activity.setContent {
            NoteListScreen(navController = TestNavHostController(LocalContext.current))
        }
    }

    @Test
    fun testingNoteListScreen_WhenLaunched_AllElementsShouldBeFound() {
        val addIconBtn = hasContentDescription("Add") and hasClickAction()
        val titleText = hasText("Notepad") and hasNoClickAction()

        composableRule.onNode(titleText).assertExists()
        composableRule.onNode(addIconBtn).assertExists()
    }
}