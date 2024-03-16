package com.gobinda.notepad.ui.screens.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuDivider(
    height: Int = 1,
    paddingStart: Int = 0,
    paddingEnd: Int = 0
) {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp)
            .padding(start = paddingStart.dp, end = paddingEnd.dp)
    )
}