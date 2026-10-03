package am.mojtaba.armengo.admin.ui.screen.reportType.sheet

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import am.mojtaba.armengo.core.domain.model.ReportType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditReportTypeSheet(
    reportType: ReportType,
    onDelete: (ReportType) -> Unit,
    onSubmit: (ReportType) -> Unit
) {
    var key by remember { mutableStateOf(reportType.key) }
    var description by remember { mutableStateOf(reportType.description) }

    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Edit ReportType", style = MaterialTheme.typography.headlineSmall)
            Row {
                IconButton (onClick = { onDelete(reportType) }) { Icon(Icons.Default.Delete, tint = Color.Red, contentDescription = null) }
                Button(onClick = { onSubmit(ReportType(id = reportType.id, key = key, description = description)) }) { Text("Save") }
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
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
