package am.mojtaba.armengo.admin.ui.screen.report.sheet

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import am.mojtaba.armengo.core.domain.model.Report

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReportSheet(
    onDismiss: () -> Unit,
    onSubmit: (Report) -> Unit
) {
    var key by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton (onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null) }
            Text("Add Report", style = MaterialTheme.typography.headlineSmall)
            Button(onClick = { if (key.isNotBlank()) { onSubmit(Report(key = key, text = text, description = description)) } }) { Text("Add") }
        }
        Spacer(Modifier.size(16.dp))

        OutlinedTextField(
            value = key,
            onValueChange = { key = it },
            label = { Text("Key") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Text") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
