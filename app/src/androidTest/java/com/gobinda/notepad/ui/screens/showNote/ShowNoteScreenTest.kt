package com.gobinda.notepad.ui.screens.showNote

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gobinda.notepad.MainActivity
import com.gobinda.notepad.di.AppModule
import com.gobinda.notepad.ui.navigation.AppScreen
import com.gobinda.notepad.ui.theme.NotepadTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule

@HiltAndroidTest
@UninstallModules(AppModule::class)
class ShowNoteScreenTest {

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composableRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composableRule.setContent {
            val navController = rememberNavController()
            NotepadTheme {
                NavHost(
                    navController = navController,
                    startDestination = AppScreen.ShowNoteScreen.router
                ) {
                    composable(route = AppScreen.ShowNoteScreen.router) {
                        ShowNoteScreen(navController = navController)
                    }
                }
            }
        }
    }

    @After
    fun tearDown() {
    }
}