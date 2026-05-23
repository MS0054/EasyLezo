package am.mojtaba.armengo.admin.ui.screen.sentence.sheet

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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import am.mojtaba.armengo.core.domain.model.Language
import am.mojtaba.armengo.core.domain.model.Sentence
import am.mojtaba.armengo.core.domain.model.Translate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditSentenceSheet(
    languages: List<Language>,
    sentence: Sentence,
    onDelete: (Sentence) -> Unit,
    onSubmit: (Sentence) -> Unit
) {

    val translationMap = remember { mutableStateMapOf<String, String>().apply {
        sentence.translations.forEach { put(it.language, it.text) }
    } }
    var level by remember { mutableStateOf(sentence.level) }
    var imageUrl by remember { mutableStateOf( sentence.image) }

    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Edit Sentence", style = MaterialTheme.typography.headlineSmall)
            Row {
                IconButton(onClick = { onDelete(sentence) }) { Icon(Icons.Default.Delete, tint = Color.Red, contentDescription = null) }
                Button(onClick = {
                    val updatedTranslations = translationMap.map { (code, text) -> Translate(language = code, text = text) }
                    onSubmit(sentence.copy(
                        categoryId = sentence.categoryId,
                        level = level,
                        image = imageUrl,
                        updatedAt = System.currentTimeMillis(),
                        translations = updatedTranslations
                    )) }) { Text("Save") }
            }
        }
        Spacer(Modifier.size(16.dp))

            LazyColumn (
                modifier = Modifier.weight(1f, fill = false),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = level,
                        onValueChange = { level = it },
                        label = { Text("Level") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                items(languages) { language ->
                    OutlinedTextField(
                        value = translationMap[language.name] ?: "",
                        onValueChange = { translationMap[language.name] = it },
                        label = { Text(language.name) },
                        placeholder = { Text(language.name) },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { AsyncImage( model = language.flag, contentDescription = null, modifier = Modifier.size(24.dp) ) }
                    )
                }
            }
        }
    }
