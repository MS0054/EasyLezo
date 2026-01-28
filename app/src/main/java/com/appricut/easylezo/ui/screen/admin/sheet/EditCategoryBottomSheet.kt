package com.appricut.easylezo.ui.screen.admin.sheet

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.appricut.easylezo.data.model.Category

@Composable
fun EditCategoryBottomSheet(
    category: Category?,
    onDismiss: () -> Unit,
    onSubmit: (Category) -> Unit
) {
    var name by remember { mutableStateOf(category?.name ?: "") }
    var image by remember { mutableStateOf(category?.image ?: "") }

    Surface(
        tonalElevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = if (category == null) "Add Category" else "Edit Category",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Category Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = image,
                onValueChange = { image = it },
                label = { Text("Image URL") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = {
                        onSubmit(
                            Category(
                                id = category?.id ?: "",
                                name = name,
                                image = image
                            )
                        )
                        onDismiss()
                    }
                ) {
                    Text(if (category == null) "Add" else "Save")
                }
            }
        }
    }
}
