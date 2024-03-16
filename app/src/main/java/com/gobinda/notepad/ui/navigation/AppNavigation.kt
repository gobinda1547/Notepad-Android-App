package com.gobinda.notepad.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gobinda.notepad.ui.screens.addEditNote.AddEditNoteScreen
import com.gobinda.notepad.ui.screens.noteList.NoteListScreen

private const val ANIMATION_OFFSET = 500
private const val ANIMATION_DURATION = 500

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        modifier = Modifier.background(MaterialTheme.colorScheme.surface),
        navController = navController,
        startDestination = AppScreen.NoteListScreen.router
    ) {
        composable(
            route = AppScreen.NoteListScreen.router,
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -ANIMATION_OFFSET },
                    animationSpec = tween(durationMillis = ANIMATION_DURATION)
                ) + fadeOut(animationSpec = tween(durationMillis = ANIMATION_DURATION))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -ANIMATION_OFFSET },
                    animationSpec = tween(durationMillis = ANIMATION_DURATION)
                ) + fadeIn(animationSpec = tween(durationMillis = ANIMATION_DURATION))
            }
        ) {
            NoteListScreen(navController = navController)
        }

        composable(
            route = AppScreen.AddOrEditNoteScreen.router + "?noteId={noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.LongType })
        ) { AddEditNoteScreen(navController = navController) }
    }
}