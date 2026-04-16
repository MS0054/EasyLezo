package com.appricut.easylezo.admin.ui.screen.category.sheet

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.appricut.easylezo.core.domain.model.Category
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategorySheet(
    maxOrder: Int,
    onDismiss: () -> Unit,
    onSubmit: (Category) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton (onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null) }
            Text("Add Category", style = MaterialTheme.typography.headlineSmall)
            Button(onClick = {
                if (name.isNotBlank()) {
                    onSubmit(Category(
                        id = UUID.randomUUID().toString(),
                        name = name.trim(),
                        image = imageUrl.trim(),
                        order = maxOrder + 1,
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()

                    )) } },) { Text("Add") }
        }
        Spacer(Modifier.size(16.dp))

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
    }

}
