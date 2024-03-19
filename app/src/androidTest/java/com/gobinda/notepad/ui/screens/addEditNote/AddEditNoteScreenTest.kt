package com.gobinda.notepad.ui.screens.addEditNote

import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class AddEditNoteScreenTest {

    /**
     * We need hilt rule cause the view model that [AddEditNoteScreen] is using
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
            AddEditNoteScreen(navController = TestNavHostController(LocalContext.current))
        }
    }

    @Test
    fun testing_WhenScreenLaunched_AllElementsShouldBeFound() {
        val doneIconBtn = hasContentDescription("Done") and hasClickAction()
        val navBackIconBtn = hasContentDescription("Back") and hasClickAction()
        val titleText = hasText("Add") and hasNoClickAction()

        composableRule.onNode(doneIconBtn).assertExists()
        composableRule.onNode(navBackIconBtn).assertExists()
        composableRule.onNode(titleText).assertExists()

        // we don't need to verify the state of view model but keeping this example
        // here to denote that there is way to access the view model from compose rule.
        val viewModel = composableRule.activity.viewModels<AddEditNoteViewModel>()
        TestCase.assertEquals(false, viewModel.value.isEditingNote.value)
    }
}