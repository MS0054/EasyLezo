package am.mojtaba.armengo.admin.ui.screen.word.sheet

import am.mojtaba.armengo.admin.ui.screen.category.sheet.move
import am.mojtaba.armengo.admin.ui.screen.sentence.sheet.DraggableLazyColumn
import am.mojtaba.armengo.core.domain.model.Word
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortCategoryWordSheet(
    words: List<Word>,
    onDismiss: () -> Unit,
    onSubmit: (orderedIds: List<String>) -> Unit
) {
    val list = remember(words) {
        mutableStateListOf<Word>().apply { addAll(words) }
    }

    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null) }
            Text("Sort Category Words", style = MaterialTheme.typography.titleLarge)
            Button(onClick = { onSubmit(list.map { it.id }) }) { Text("Save") }
        }
        Spacer(Modifier.size(16.dp))

        DraggableLazyColumn(
            items = list,
            key = { item -> item.id },
            onMove = { from, to -> list.move(from, to) }
        ) { word, isDragging ->
            WordDragItem(word, isDragging)
        }
    }
}
