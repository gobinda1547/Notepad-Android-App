package com.gobinda.notepad.ui.screens.showNote

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gobinda.notepad.R
import com.gobinda.notepad.ui.navigation.AppScreen
import com.gobinda.notepad.ui.screens.common.ContentHolderForTitledScreen
import com.gobinda.notepad.ui.screens.common.TestTag

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowNoteScreen(
    navController: NavController,
    viewModel: ShowNoteViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val currentNote = viewModel.currentNote.collectAsState()
    val verticalScrollState = rememberScrollState()
    val showDeleteDialog = remember { mutableStateOf(false) }

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

    if (showDeleteDialog.value) {
        DeleteNoteDialog(
            onConfirm = {
                currentNote.value?.id?.let {
                    viewModel.handleEvent(ShowNoteUiEvent.DeleteNote(it))
                }
                showDeleteDialog.value = false
            },
            onDismiss = {
                showDeleteDialog.value = false
            }
        )
    }

    fun openAddOrEditScreen(noteId: Long?) {
        noteId?.let { nId ->
            navController.navigate(
                AppScreen.AddOrEditNoteScreen.createRoute(nId)
            )
        }
    }

    Scaffold(
        modifier = Modifier.testTag(TestTag.ShowNoteScreenView),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground
                ),
                title = { Text(text = stringResource(R.string.text_read)) },
                actions = {
                    IconButton(
                        modifier = Modifier.testTag(TestTag.ShowNoteScreenDeleteIconBtn),
                        onClick = { showDeleteDialog.value = true }
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                    }
                    IconButton(
                        modifier = Modifier.testTag(TestTag.ShowNoteScreenEditIconBtn),
                        onClick = { openAddOrEditScreen(currentNote.value?.id) }
                    ) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                    }
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.testTag(TestTag.ShowNoteScreenBackBtn),
                        onClick = { navController.navigateUp() }
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
            currentNote.value?.let {
                Column(modifier = Modifier.verticalScroll(verticalScrollState)) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        text = it.content,
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 20.sp,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Light
                        )
                    )
                }
            }
        }
    }
}