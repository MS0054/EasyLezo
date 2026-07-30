package am.mojtaba.armengo.admin.ui.screen.word.sheet

import am.mojtaba.armengo.admin.ui.component.LanguageAwareText
import am.mojtaba.armengo.core.domain.model.Word
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignWordsSheet(
    words: List<Word>,
    initiallySelectedIds: List<String>,
    onDismiss: () -> Unit,
    onSubmit: (selectedIds: List<String>) -> Unit
) {
    val selectedIds = remember { mutableStateListOf<String>().apply { addAll(initiallySelectedIds) } }
    var searchQuery by remember { mutableStateOf("") }
    val filteredWords by remember(searchQuery, words) {
        derivedStateOf {
            if (searchQuery.isBlank()) {
                words
            } else {
                words.filter { word ->
                    word.fromText.contains(searchQuery, ignoreCase = true) ||
                            word.toText.contains(searchQuery, ignoreCase = true)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        // --- Header Section ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onDismiss) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }
            Text(
                text = "Assign Words",
                style = MaterialTheme.typography.titleLarge
            )
            Button(
                onClick = { onSubmit(selectedIds.toList()) }
            ) {
                Text("Save")
            }
        }

        Spacer(Modifier.size(16.dp))

        // --- Search Bar ---
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search words...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search")
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            singleLine = true
        )

        Spacer(Modifier.size(16.dp))

        // --- Words List with Checkbox ---
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(filteredWords, key = { it.id }) { word ->
                val isChecked = selectedIds.contains(word.id)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            if (isChecked) {
                                selectedIds.remove(word.id)
                            } else {
                                selectedIds.add(word.id)
                            }
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = if (isChecked) {
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant
                        }
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { checked ->
                                if (checked == true) {
                                    selectedIds.add(word.id)
                                } else {
                                    selectedIds.remove(word.id)
                                }
                            }
                        )

                        AsyncImage(
                            model = word.image,
                            contentDescription = null,
                            modifier = Modifier
                                .size(48.dp)
                                .padding(horizontal = 8.dp),
                            contentScale = ContentScale.Fit
                        )

                        Column(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .weight(1f)
                        ) {
                            LanguageAwareText(
                                text = word.fromText,
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.titleMedium
                            )
                            LanguageAwareText(
                                text = word.toText,
                                fontSize = 13.sp,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}
