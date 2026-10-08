package am.mojtaba.armengo.admin.ui.screen.reportStatusType.sheet

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import am.mojtaba.armengo.core.domain.model.ReportStatusType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditReportStatusTypeSheet(
    reportStatusType: ReportStatusType,
    onDelete: (ReportStatusType) -> Unit,
    onSubmit: (ReportStatusType) -> Unit
) {
    var key by remember { mutableStateOf(reportStatusType.key) }
    var color by remember { mutableStateOf(reportStatusType.color) }
    var description by remember { mutableStateOf(reportStatusType.description) }

    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Edit ReportStatusType", style = MaterialTheme.typography.headlineSmall)
            Row {
                IconButton (onClick = { onDelete(reportStatusType) }) { Icon(Icons.Default.Delete, tint = Color.Red, contentDescription = null) }
                Button(onClick = { onSubmit(ReportStatusType(id = reportStatusType.id, key = key, color = color, description = description)) }) { Text("Save") }
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
            value = color,
            onValueChange = { color = it },
            label = { Text("Color") },
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
