package am.mojtaba.armengo.admin.ui.screen.report.sheet

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import am.mojtaba.armengo.core.domain.model.Report

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditReportSheet(
    report: Report,
    onDelete: (Report) -> Unit,
    onSubmit: (Report) -> Unit
) {
    var key by remember { mutableStateOf(report.key) }
    var text by remember { mutableStateOf(report.text) }
    var description by remember { mutableStateOf(report.description) }

    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Edit Report", style = MaterialTheme.typography.headlineSmall)
            Row {
                IconButton (onClick = { onDelete(report) }) { Icon(Icons.Default.Delete, tint = Color.Red, contentDescription = null) }
                Button(onClick = { onSubmit(Report(id = report.id, key = key, text = text, description = description)) }) { Text("Save") }
            }
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
