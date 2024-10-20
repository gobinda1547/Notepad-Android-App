package com.gobinda.notepad.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gobinda.notepad.ui.theme.NotepadTheme

/**
 * [ContentHolderForTitledScreen] - the main reason for declaring this composable.
 * 1. We need to cover entire screen with padding value
 * 2. We need to draw a horizontal divider between screen title and contents.
 * 3. Background color have to be the same as activity background color.
 */
@Composable
fun ContentHolderForTitledScreen(
    paddingValues: PaddingValues = PaddingValues(0.dp, 0.dp, 0.dp, 0.dp),
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(color = backgroundColor)
    ) {
        GradientHorizontalDivider(
            height = 2,
            paddingStart = 0,
            paddingEnd = 0,
            startColor = MaterialTheme.colorScheme.background,
            endColor = MaterialTheme.colorScheme.surfaceVariant
        )
        content()
    }
}

@Preview(showSystemUi = true)
@Composable
private fun DefaultScreenContentHolderPreview() {
    NotepadTheme {
        ContentHolderForTitledScreen {

        }
    }
}