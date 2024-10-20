package com.gobinda.notepad.ui.screens.noteList

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gobinda.notepad.domain.model.NoteAsListItem
import com.gobinda.notepad.ui.common.ContentHolderForTitledScreen
import com.gobinda.notepad.ui.navigation.AppScreen
import com.gobinda.notepad.ui.screens.common.TestTag

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    navController: NavController,
    viewModel: NoteListViewModel = hiltViewModel()
) {
    val noteItems: State<List<NoteAsListItem>?> = viewModel.noteList.collectAsState()

    fun openAddOrEditScreen(noteId: Long) {
        val route = AppScreen.AddOrEditNoteScreen.router
        val param = "noteId=$noteId"
        navController.navigate("$route?$param")
    }

    fun openSingleNoteScreen(noteId: Long) {
        val route = AppScreen.ShowNoteScreen.router
        val param = "noteId=$noteId"
        navController.navigate("$route?$param")
    }

    Scaffold(
        modifier = Modifier.testTag(TestTag.NoteListScreenView),
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground
                ),
                title = { Text(text = "Notepad") },
                actions = {
                    IconButton(
                        modifier = Modifier.testTag(TestTag.NoteListScreenAddIconBtn),
                        onClick = { openAddOrEditScreen(-1) }
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                    }
                }
            )
        }
    ) { innerPadding ->
        ContentHolderForTitledScreen(paddingValues = innerPadding) {
            noteItems.value?.let { validItems ->
                when (validItems.isEmpty()) {
                    true -> NoteListEmptyView {
                        openAddOrEditScreen(-1)
                    }

                    else -> NoteListNonEmptyValue(noteItems = validItems) {
                        openSingleNoteScreen(it)
                    }
                }
            }
        }
    }
}