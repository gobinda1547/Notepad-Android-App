package com.gobinda.notepad.ui.screens.noteList

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.gobinda.notepad.domain.model.NoteAsListItem
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gobinda.notepad.ui.screens.common.MenuDivider
import com.gobinda.notepad.ui.screens.common.TestTag

@Composable
fun NoteListNonEmptyValue(
    noteItems: List<NoteAsListItem>,
    onItemClicked: (itemId: Long) -> Unit
) {
    LazyColumn(modifier = Modifier.testTag(TestTag.NoteListNonEmptyView_LazyColumn)) {
        items(
            count = noteItems.size,
            key = { i -> i }
        ) {
            NoteViewAsListItem(
                noteAsListItem = noteItems[it],
                onItemClick = onItemClicked
            )
            MenuDivider()
        }
    }
}

@Composable
private fun NoteViewAsListItem(
    noteAsListItem: NoteAsListItem,
    onItemClick: (itemId: Long) -> Unit
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onItemClick(noteAsListItem.id) }
            .padding(16.dp),
        text = noteAsListItem.title,
        color = MaterialTheme.colorScheme.onSurface,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp
    )
}