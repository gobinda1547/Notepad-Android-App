package com.gobinda.notepad.ui.screens.addEditNote

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gobinda.notepad.ui.screens.common.ContentHolderForTitledScreen
import com.gobinda.notepad.ui.screens.common.TestTag

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val contentTextState = viewModel.contentText.collectAsState()
    val isEditingNoteState = viewModel.isEditingNote.collectAsState()
    val verticalScrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect {
            it?.let { resourceId ->
                val actualMessage = context.getString(resourceId)
                Toast.makeText(context, actualMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.shouldCloseScreen.collect { shouldClose ->
            if (shouldClose == true) {
                navController.navigateUp()
            }
        }
    }

    Scaffold(
        modifier = Modifier.testTag(TestTag.AddEditNoteScreenView),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground
                ),
                title = {
                    isEditingNoteState.value?.let { isEdit ->
                        Text(
                            modifier = Modifier.testTag(TestTag.AddEditScreeTitleTextView),
                            text = if (isEdit) "Edit" else "Add"
                        )
                    }
                },
                actions = {
                    IconButton(
                        modifier = Modifier.testTag(TestTag.AddEditScreenDoneIconBtn),
                        onClick = { viewModel.handleEvent(AddEditUiEvent.SaveNote) }
                    ) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = "Done")
                    }
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.testTag(TestTag.AddEditScreenBackBtn),
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        ContentHolderForTitledScreen(paddingValues = innerPadding) {
            Column(
                modifier = Modifier
                    .verticalScroll(verticalScrollState)
                    .imePadding()
            ) {
                ContentInputView(text = contentTextState.value) {
                    viewModel.handleEvent(AddEditUiEvent.UpdateContent(it))
                }
            }
        }
    }
}