package am.mojtaba.armengo.admin.ui.screen.word.sheet

import am.mojtaba.armengo.core.domain.model.Language
import am.mojtaba.armengo.core.domain.model.Translate
import am.mojtaba.armengo.core.domain.model.Word
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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditWordSheet(
    languages: List<Language>,
    word: Word,
    onDelete: (String) -> Unit,
    onSubmit: (Word) -> Unit
) {

    val translationMap = remember { mutableStateMapOf<String, String>().apply {
        word.translations.forEach { put(it.language, it.text) }
    } }
    var level by remember { mutableStateOf(word.level) }
    var imageUrl by remember { mutableStateOf( word.image) }
    var voiceUrl by remember { mutableStateOf(word.voiceUrl) }
    var hasVoice by remember { mutableStateOf(word.hasVoice) }


    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Edit Word", style = MaterialTheme.typography.headlineSmall)
            Row {
                IconButton(onClick = { onDelete(word.id) }) { Icon(Icons.Default.Delete, tint = Color.Red, contentDescription = null) }
                Button(onClick = {
                    val updatedTranslations = translationMap.map { (code, text) -> Translate(language = code, text = text) }
                    onSubmit(word.copy(
                        categoryId = word.categoryId,
                        level = level,
                        image = imageUrl,
                        updatedAt = System.currentTimeMillis(),
                        voiceUrl = voiceUrl,
                        hasVoice = hasVoice,
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

                    OutlinedTextField(
                        value = voiceUrl,
                        onValueChange = { voiceUrl = it },
                        label = { Text("VoiceUrl") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = imageUrl,
                        onValueChange = { imageUrl = it },
                        label = { Text("ImageUrl") },
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

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("HasVoice")
                        Switch(
                            checked = hasVoice,
                            onCheckedChange = { hasVoice = it }
                        )
                    }
                }
            }
        }
    }
