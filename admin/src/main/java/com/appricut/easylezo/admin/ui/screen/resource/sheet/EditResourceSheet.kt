package com.appricut.easylezo.admin.ui.screen.resource.sheet

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.appricut.easylezo.core.domain.model.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditResourceSheet(
    resource: Resource,
    onDelete: (Resource) -> Unit,
    onSubmit: (Resource) -> Unit
) {
    var name by remember { mutableStateOf(resource.name) }
    var description by remember { mutableStateOf(resource.description) }
    var type by remember { mutableStateOf(resource.type) }
    var url by remember { mutableStateOf(resource.url) }


    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        Row (Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Edit Resource", style = MaterialTheme.typography.headlineSmall)
            Row {
                IconButton (onClick = { onDelete(resource)}) { Icon(Icons.Default.Delete, tint = Color.Red, contentDescription = null) }
                Button(onClick = {onSubmit(Resource(id = resource.id, name = name, description = description, type = type, url = url)) }) { Text("Save") }
            }
        }
        Spacer(Modifier.size(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("URL") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = type,
            onValueChange = { type = it },
            label = { Text("Type")},
            modifier = Modifier.fillMaxWidth()
        )
    }

}
