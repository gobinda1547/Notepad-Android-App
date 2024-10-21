package com.gobinda.notepad.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gobinda.notepad.ui.screens.addEditNote.AddEditNoteScreen
import com.gobinda.notepad.ui.screens.noteList.NoteListScreen
import com.gobinda.notepad.ui.screens.showNote.ShowNoteScreen

private const val ANIMATION_DURATION = 300

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val animationOffset = calculateScreenWidthInPixel()

    NavHost(
        modifier = Modifier.background(MaterialTheme.colorScheme.surface),
        navController = navController,
        startDestination = AppScreen.NoteListScreen.route
    ) {
        composable(
            route = AppScreen.NoteListScreen.route,
            arguments = AppScreen.NoteListScreen.navArguments,
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -animationOffset },
                    animationSpec = tween(durationMillis = ANIMATION_DURATION)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -animationOffset },
                    animationSpec = tween(durationMillis = ANIMATION_DURATION)
                )
            }
        ) {
            NoteListScreen(navController = navController)
        }

        composable(
            route = AppScreen.AddOrEditNoteScreen.route,
            arguments = AppScreen.AddOrEditNoteScreen.navArguments,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { animationOffset },
                    animationSpec = tween(durationMillis = ANIMATION_DURATION)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { animationOffset },
                    animationSpec = tween(durationMillis = ANIMATION_DURATION)
                )
            }
        ) { AddEditNoteScreen(navController = navController) }

        composable(
            route = AppScreen.ShowNoteScreen.route,
            arguments = AppScreen.ShowNoteScreen.navArguments,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { animationOffset },
                    animationSpec = tween(durationMillis = ANIMATION_DURATION)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -animationOffset },
                    animationSpec = tween(durationMillis = ANIMATION_DURATION)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -animationOffset },
                    animationSpec = tween(durationMillis = ANIMATION_DURATION)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { animationOffset },
                    animationSpec = tween(durationMillis = ANIMATION_DURATION)
                )
            }
        ) { ShowNoteScreen(navController = navController) }
    }
}

@Composable
private fun calculateScreenWidthInPixel(): Int {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val density = LocalDensity.current.density
    return (screenWidthDp * density).toInt()
}