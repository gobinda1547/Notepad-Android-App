package com.gobinda.notepad.ui.screens.common

import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import com.gobinda.notepad.ui.theme.NotepadTheme
import org.junit.Rule
import org.junit.Test

class MenuDividerTest {

    companion object {
        private const val TEST_HEIGHT_ONE = 1
        private const val TEST_HEIGHT_TWO = 2
    }

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun testingMenuDivider_heightIsEqualToTheInputOne() {
        composeRule.setContent {
            NotepadTheme {
                MenuDivider(height = TEST_HEIGHT_ONE)
            }
        }

        val menuDividerMatcher = hasTestTag(TestTag.MenuDivider_Main)
        composeRule.onNode(menuDividerMatcher).assertHeightIsEqualTo(TEST_HEIGHT_ONE.dp)
    }

    @Test
    fun testingMenuDivider_heightIsEqualToTheInputTwo() {
        composeRule.setContent {
            NotepadTheme {
                MenuDivider(height = TEST_HEIGHT_TWO)
            }
        }

        val menuDividerMatcher = hasTestTag(TestTag.MenuDivider_Main)
        composeRule.onNode(menuDividerMatcher).assertHeightIsEqualTo(TEST_HEIGHT_TWO.dp)
    }
}