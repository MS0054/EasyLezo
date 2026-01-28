package com.appricut.easylezo.ui.screen.admin.sheet

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.appricut.easylezo.data.model.Sentence

@Composable
fun EditSentenceBottomSheet(
    sentence: Sentence?,
    onDismiss: () -> Unit,
    onSubmit: (Sentence) -> Unit
) {
    var ar by remember { mutableStateOf(sentence?.ar ?: "") }
    var fa by remember { mutableStateOf(sentence?.fa ?: "") }
    var en by remember { mutableStateOf(sentence?.en ?: "") }
    var level by remember { mutableStateOf(sentence?.level ?: "") }
    var image by remember { mutableStateOf(sentence?.image ?: "") }

    Surface(
        tonalElevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = if (sentence == null) "Add Sentence" else "Edit Sentence",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(value = ar, onValueChange = { ar = it }, label = { Text("Arabic") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(value = fa, onValueChange = { fa = it }, label = { Text("Persian") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(value = en, onValueChange = { en = it }, label = { Text("English") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(value = level, onValueChange = { level = it }, label = { Text("Level") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(value = image, onValueChange = { image = it }, label = { Text("Image URL") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onDismiss) { Text("Cancel") }
                Spacer(Modifier.width(8.dp))
                Button(onClick = {
                    onSubmit(
                        Sentence(
                            id = sentence?.id ?: "",
                            ar = ar,
                            fa = fa,
                            en = en,
                            image = image,
                            level = level
                        )
                    )
                    onDismiss()
                }) {
                    Text(if (sentence == null) "Add" else "Save")
                }
            }
        }
    }
}
