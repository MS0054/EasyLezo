package am.mojtaba.armengo.admin.ui.screen.sentence.sheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import am.mojtaba.armengo.core.domain.model.Language
import am.mojtaba.armengo.core.domain.model.Sentence
import am.mojtaba.armengo.core.domain.model.SentenceAlternative
import am.mojtaba.armengo.core.domain.model.Translate
import am.mojtaba.armengo.core.domain.model.Word
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSentenceSheet(
    languages: List<Language>,
    availableWords: List<Word>, // لیست کل لغات دریافت شده از کالکشن Word
    onDismiss: () -> Unit,
    onSubmit: (Sentence) -> Unit
) {
    val translationMap = remember { mutableStateMapOf<String, String>() }
    val phoneticMap = remember { mutableStateMapOf<String, String>() }
    var level by remember { mutableStateOf("") }
    var voiceUrl by remember { mutableStateOf("") }
    var hasVoice by remember { mutableStateOf(false) }

    // مدیریت لیست جایگزین‌ها (Alternative Slots)
    val alternativesList = remember { mutableStateListOf<SentenceAlternative>() }

    // شناسه ایندکس اسلاتی که دیالوگ انتخاب کلمه برای آن باز شده است
    var activeSlotIndexForWordPicker by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onDismiss) {
                Icon(Icons.Default.Close, contentDescription = null)
            }
            Text("Add Sentence", style = MaterialTheme.typography.headlineSmall)
            Button(onClick = {
                val translations = translationMap.map { (code, text) ->
                    Translate(
                        language = code,
                        text = text,
                        phonetic = phoneticMap[code] ?: ""
                    )
                }

                val sentence = Sentence(
                    id = UUID.randomUUID().toString(),
                    level = level,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis(),
                    voiceUrl = voiceUrl,
                    hasVoice = hasVoice,
                    translations = translations,
                    alternatives = alternativesList.toList()
                )
                onSubmit(sentence)
            }) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
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

            item {
                OutlinedTextField(
                    value = voiceUrl,
                    onValueChange = { voiceUrl = it },
                    label = { Text("VoiceUrl") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            items(languages) { language ->
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedTextField(
                        value = translationMap[language.name] ?: "",
                        onValueChange = { translationMap[language.name] = it },
                        label = { Text(language.name) },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            AsyncImage(
                                model = language.flag,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )
                    OutlinedTextField(
                        value = phoneticMap[language.name] ?: "",
                        onValueChange = { phoneticMap[language.name] = it },
                        label = { Text("${language.name} Phonetic") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
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

            // بخش مدیریت Alternative
            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Alternatives", style = MaterialTheme.typography.titleMedium)
                    IconButton(onClick = {
                        alternativesList.add(SentenceAlternative())
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Alternative Slot")
                    }
                }
            }

            items(alternativesList.size) { index ->
                val item = alternativesList[index]
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            OutlinedTextField(
                                value = item.slotKey,
                                onValueChange = { newKey ->
                                    alternativesList[index] = item.copy(slotKey = newKey)
                                },
                                label = { Text("Target Word (e.g. spicy)") },
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = { alternativesList.removeAt(index) }) {
                                Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = { activeSlotIndexForWordPicker = index },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Select Words (${item.wordIds.size} Selected)")
                        }
                    }
                }
            }
        }
    }

    // دیالوگ Multi-select برای انتخاب لغات از کالکشن Word
    activeSlotIndexForWordPicker?.let { index ->
        val currentSlot = alternativesList[index]
        WordSelectionDialog(
            availableWords = availableWords,
            selectedWordIds = currentSlot.wordIds,
            onDismiss = { activeSlotIndexForWordPicker = null },
            onConfirm = { updatedWordIds ->
                alternativesList[index] = currentSlot.copy(wordIds = updatedWordIds)
                activeSlotIndexForWordPicker = null
            }
        )
    }
}
