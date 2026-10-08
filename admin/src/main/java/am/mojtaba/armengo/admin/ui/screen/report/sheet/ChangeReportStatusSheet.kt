package am.mojtaba.armengo.admin.ui.screen.report.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import am.mojtaba.armengo.admin.ui.screen.report.parseHexColor
import am.mojtaba.armengo.core.domain.model.ReportStatusType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeReportStatusSheet(
    statusTypes: List<ReportStatusType>,
    currentStatus: ReportStatusType,
    onDismiss: () -> Unit,
    onSubmit: (ReportStatusType) -> Unit
) {
    var selectedStatus by remember { mutableStateOf(currentStatus) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onDismiss) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }
            Text(
                text = "Change Report Status",
                style = MaterialTheme.typography.titleLarge
            )
            Button(
                onClick = { onSubmit(selectedStatus) }
            ) {
                Text("Save")
            }
        }

        Spacer(Modifier.height(16.dp))

        if (statusTypes.isEmpty()) {
            Text(
                text = "No status types available",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(statusTypes) { statusType ->
                    val isSelected = selectedStatus.key == statusType.key
                    val statusColor = parseHexColor(statusType.color)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedStatus = statusType
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) {
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                            } else {
                                MaterialTheme.colorScheme.surfaceVariant
                            }
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { selectedStatus = statusType }
                            )

                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .size(16.dp)
                                    .background(statusColor, shape = CircleShape)
                            )

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = statusType.key,
                                    fontSize = 16.sp,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                if (statusType.description.isNotBlank()) {
                                    Text(
                                        text = statusType.description,
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))
    }
}
