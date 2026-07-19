package am.mojtaba.armengo.admin.ui.screen.word.sheet

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
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import am.mojtaba.armengo.core.domain.model.Language
import am.mojtaba.armengo.core.domain.model.Translate
import am.mojtaba.armengo.core.domain.model.Word
import android.util.Log
import androidx.compose.material3.Switch
import androidx.compose.ui.Alignment
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWordSheet(
    languages: List<Language>,
    categoryId: String,
    maxOrder: Int,
    onDismiss: () -> Unit,
    onSubmit: (Word) -> Unit
) {
    Log.i("TOTO", "categoryId: $categoryId")
    val translationMap = remember { mutableStateMapOf<String, String>() }
    var level by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var voiceUrl by remember { mutableStateOf("") }
    var hasVoice by remember { mutableStateOf(false) }


    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton (onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null) }
            Text("Add Word", style = MaterialTheme.typography.headlineSmall)
            Button(onClick = {
                val translations = translationMap.map { (code, text) -> Translate(language = code, text = text) }
                onSubmit( Word(
                    id = UUID.randomUUID().toString(),
                    categoryId = categoryId,
                    level = level,
                    image = imageUrl,
                    order = maxOrder + 1,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis(),
                    voiceUrl = voiceUrl,
                    hasVoice = hasVoice,
                    translations = translations
                ))
            }) { Text("Add") }
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
