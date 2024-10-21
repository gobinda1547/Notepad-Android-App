package com.gobinda.notepad.ui.screens.addEditNote

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gobinda.notepad.R
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
    val keyboardController = LocalSoftwareKeyboardController.current

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
                keyboardController?.hide()
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
                            text = when (isEdit) {
                                true -> stringResource(R.string.text_edit)
                                else -> stringResource(R.string.text_new)
                            }
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
                        onClick = {
                            keyboardController?.hide()
                            navController.navigateUp()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ContentHolderForTitledScreen(paddingValues = innerPadding) {
            ContentInputView(text = contentTextState.value) {
                viewModel.handleEvent(AddEditUiEvent.UpdateContent(it))
            }
        }
    }
}