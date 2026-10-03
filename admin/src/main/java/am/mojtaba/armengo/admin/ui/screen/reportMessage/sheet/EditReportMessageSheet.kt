package am.mojtaba.armengo.admin.ui.screen.reportMessage.sheet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import am.mojtaba.armengo.core.domain.model.ReportMessage
import am.mojtaba.armengo.core.domain.model.ReportType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditReportMessageSheet(
    availableReportTypes: List<ReportType>,
    reportMessage: ReportMessage,
    onDelete: (ReportMessage) -> Unit,
    onSubmit: (ReportMessage) -> Unit
) {
    var key by remember { mutableStateOf(reportMessage.key) }
    var text by remember { mutableStateOf(reportMessage.text) }
    var icon by remember { mutableStateOf(reportMessage.icon) }
    var description by remember { mutableStateOf(reportMessage.description) }
    val selectedReportTypes = remember { mutableStateListOf<String>().apply { addAll(reportMessage.reportTypes) } }

    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Edit ReportMessage", style = MaterialTheme.typography.headlineSmall)
            Row {
                IconButton (onClick = { onDelete(reportMessage) }) { Icon(Icons.Default.Delete, tint = Color.Red, contentDescription = null) }
                Button(
                    onClick = {
                        onSubmit(
                            ReportMessage(
                                id = reportMessage.id,
                                key = key,
                                text = text,
                                icon = icon,
                                description = description,
                                reportTypes = selectedReportTypes.toList()
                            )
                        )
                    }
                ) {
                    Text("Save")
                }
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
            value = icon,
            onValueChange = { icon = it },
            label = { Text("Icon") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.size(12.dp))
        Text("Report Types (Multi-Select):", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.size(8.dp))

        if (availableReportTypes.isEmpty()) {
            Text("No ReportTypes available", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
        } else {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(availableReportTypes) { reportType ->
                    val isSelected = selectedReportTypes.contains(reportType.key)
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            if (isSelected) {
                                selectedReportTypes.remove(reportType.key)
                            } else {
                                selectedReportTypes.add(reportType.key)
                            }
                        },
                        label = { Text(reportType.key) }
                    )
                }
            }
        }
    }
}
