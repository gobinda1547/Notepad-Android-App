package com.gobinda.notepad.ui.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun MenuDivider(
    height: Int = 2,
    paddingStart: Int = 16,
    paddingEnd: Int = 16,
    startColor: Color = MaterialTheme.colorScheme.surface,
    endColor: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp)
            .padding(start = paddingStart.dp, end = paddingEnd.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(startColor, endColor)
                )
            )
            .testTag(TestTag.MenuDivider_Main)
    )
}