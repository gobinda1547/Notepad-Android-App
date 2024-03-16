package com.gobinda.notepad.ui.screens.showNote

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.gobinda.notepad.R

@Composable
fun DeleteNoteDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
                onDismiss()
            },
            title = { Text(stringResource(id = R.string.delete_dialog_title)) },
            text = { Text(stringResource(id = R.string.delete_dialog_content)) },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        onConfirm()
                    }
                ) {
                    Text(stringResource(id = R.string.delete_dialog_positive_button_text))
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        onDismiss()
                    }
                ) {
                    Text(stringResource(id = R.string.delete_dialog_negative_button_text))
                }
            }
        )
    }
}