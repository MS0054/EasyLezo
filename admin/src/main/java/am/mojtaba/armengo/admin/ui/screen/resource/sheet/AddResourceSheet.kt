package am.mojtaba.armengo.admin.ui.screen.resource.sheet

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import am.mojtaba.armengo.core.domain.model.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddResourceSheet(
    onDismiss: () -> Unit,
    onSubmit: (Resource) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }


    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton (onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null) }
            Text("Add Category", style = MaterialTheme.typography.headlineSmall)
            Button(onClick = { if (name.isNotBlank()) { onSubmit(Resource(name = name, description = description, type = type, url = url)) } },) { Text("Add") }
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
