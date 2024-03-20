package com.gobinda.notepad.ui.screens.showNote

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.testing.TestNavHostController
import com.gobinda.notepad.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * We will write only instrumentation test for this class here. So we will
 * not verify test cases like - navigation will happen when user will click
 * on the add icon cause that requires extra setup but we will cover these
 * test cases when writing end to end (integration) test case. For now since
 * [ShowNoteScreen] needed navController as input parameter so we will pass
 * [TestNavHostController] as nacController, but as I have stated above - in
 * the test cases - it won't be used. We will only verify that our necessary
 * options are available on the screen.
 */
@HiltAndroidTest
class ShowNoteScreenTest {

    /**
     * We need hilt rule cause the view model that [ShowNoteScreen] is using
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
            ShowNoteScreen(navController = TestNavHostController(LocalContext.current))
        }
    }

    @Test
    fun testing_WhenScreenLaunched_AllElementsShouldBeFound() {
        val editIconBtn = hasContentDescription("Edit")
        val deleteIconButton = hasContentDescription("Delete")
        val navBackIconBtn = hasContentDescription("Back")

        composableRule.onNode(editIconBtn).assertExists().assertHasClickAction()
        composableRule.onNode(deleteIconButton).assertExists().assertHasClickAction()
        composableRule.onNode(navBackIconBtn).assertExists().assertHasClickAction()
    }
}