package com.appricut.easylezo.ui.screen.admin.sheet

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.appricut.easylezo.domain.model.Sentence

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSentenceBottomSheet(
    onDismiss: () -> Unit,
    onSubmit: (Sentence) -> Unit
) {
    var ar by remember { mutableStateOf("") }
    var fa by remember { mutableStateOf("") }
    var en by remember { mutableStateOf("") }
    var level by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            Text(
                text = "Add Sentence",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
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

            OutlinedTextField(value = image, onValueChange = { image = it }, label = { Text("Image URL (optional)") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    if (ar.isNotBlank() || fa.isNotBlank() || en.isNotBlank()) {
                        onSubmit(
                            Sentence(
                                id = "", // Firestore تولید می‌کند
                                ar = ar.trim(),
                                fa = fa.trim(),
                                en = en.trim(),
                                image = image.trim(),
                                level = level.trim()
                            )
                        )
                        onDismiss()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Add Sentence")
            }

            Spacer(Modifier.height(10.dp))
        }
    }
}
