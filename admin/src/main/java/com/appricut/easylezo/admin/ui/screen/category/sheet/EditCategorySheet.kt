package com.appricut.easylezo.admin.ui.screen.category.sheet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.appricut.easylezo.core.domain.model.Category

@Composable
fun EditCategorySheet(
    category: Category,
    onSubmit: (Category) -> Unit,
    onDelete: (Category) -> Unit
) {

    var name by remember { mutableStateOf(category.name) }
    var image by remember { mutableStateOf(category.image) }

    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        Row (Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Edit Category", style = MaterialTheme.typography.headlineSmall)
            Row {
                IconButton (onClick = { onDelete(category)}) { Icon(Icons.Default.Delete, tint = Color.Red, contentDescription = null) }
                Button(onClick = {
                    onSubmit(category.copy(
                        id = category.id,
                        name = name,
                        image = image,
                        updatedAt = System.currentTimeMillis()
                    )) }) { Text("Save") }
            }
        }
        Spacer(Modifier.size(16.dp))

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
    }
}
