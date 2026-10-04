package am.mojtaba.armengo.admin.ui.screen.reportMessage.sheet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import am.mojtaba.armengo.core.domain.model.ReportMessage
import am.mojtaba.armengo.core.domain.model.ReportType
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReportMessageSheet(
    availableReportTypes: List<ReportType>,
    onDismiss: () -> Unit,
    onSubmit: (ReportMessage) -> Unit
) {
    var key by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    var icon by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val selectedReportTypes = remember { mutableStateListOf<String>() }

    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton (onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null) }
            Text("Add ReportMessage", style = MaterialTheme.typography.headlineSmall)
            Button(
                onClick = {
                    if (key.isNotBlank()) {
                        onSubmit(
                            ReportMessage(
                                id = UUID.randomUUID().toString(),
                                key = key,
                                text = text,
                                icon = icon,
                                description = description,
                                reportTypes = selectedReportTypes.toList()
                            )
                        )
                    }
                }
            ) {
                Text("Add")
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
