package com.gobinda.notepad.ui.screens.showNote

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.gobinda.notepad.R
import com.gobinda.notepad.ui.screens.common.TestTag

@Composable
fun DeleteNoteDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            modifier = Modifier.testTag(TestTag.DeleteNoteDialogView),
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            onDismissRequest = {
                openDialog.value = false
                onDismiss()
            },
            title = { Text(stringResource(id = R.string.delete_dialog_title)) },
            text = { Text(stringResource(id = R.string.delete_dialog_content)) },
            confirmButton = {
                Button(
                    modifier = Modifier.testTag(TestTag.DeleteNoteDialogYesBtn),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    onClick = {
                        openDialog.value = false
                        onConfirm()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.delete_dialog_positive_button_text),
                        color = Color.White
                    )
                }
            },
            dismissButton = {
                Button(
                    modifier = Modifier.testTag(TestTag.DeleteNoteDialogNoBtn),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                    onClick = {
                        openDialog.value = false
                        onDismiss()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.delete_dialog_negative_button_text),
                        color = Color.Black
                    )
                }
            }
        )
    }
}