package com.appricut.easylezo.ui.screen.admin.sheet

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.appricut.easylezo.data.model.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategoryBottomSheet(
    onDismiss: () -> Unit,
    onSubmit: (Category) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            Text(
                "Add New Category",
                style = MaterialTheme.typography.headlineSmall
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Category Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = { Text("Image URL (optional)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(10.dp))

            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        onSubmit(
                            Category(
                                id = "",   // Firestore خودش تولید می‌کند
                                name = name.trim(),
                                image = imageUrl.trim()
                            )
                        )
                        onDismiss()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Add Category")
            }

            Spacer(Modifier.height(10.dp))
        }
    }
}
