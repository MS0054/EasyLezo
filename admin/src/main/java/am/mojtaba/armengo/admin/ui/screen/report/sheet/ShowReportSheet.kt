package am.mojtaba.armengo.admin.ui.screen.report.sheet

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import am.mojtaba.armengo.core.domain.model.Report
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowReportSheet(
    report: Report,
    onDismiss: () -> Unit,
    onInspectItem: (Report) -> Unit = {}
) {
    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) }
    val formattedDate = remember(report.createdAt) {
        if (report.createdAt > 0) dateFormat.format(Date(report.createdAt)) else ""
    }

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
            Text(
                text = "Report Details",
                style = MaterialTheme.typography.headlineSmall
            )
            IconButton(onClick = onDismiss) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }
        }

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ReportDetailItem(label = "Report ID", value = report.id)
                ReportDetailItem(label = "Report Type", value = report.reportType)
                ReportDetailItem(label = "Report Message Key", value = report.reportMessageKey)

                if (report.itemId.isNotBlank()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ReportDetailItem(label = "Item ID", value = report.itemId, modifier = Modifier.weight(1f))
                        if (report.reportType.equals("Sentence", ignoreCase = true) || report.reportType.equals("Word", ignoreCase = true)) {
                            IconButton(onClick = { onInspectItem(report) }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "View Item"
                                )
                            }
                        }
                    }
                }

                ReportDetailItem(label = "User Comment", value = report.userComment)
                ReportDetailItem(label = "App Version", value = report.appVersion)
                ReportDetailItem(label = "Device Info", value = report.deviceInfo)
                ReportDetailItem(label = "User App Language", value = report.userAppLanguage)
                if (formattedDate.isNotEmpty()) {
                    ReportDetailItem(label = "Created At", value = formattedDate)
                }
            }
        }

        Spacer(Modifier.height(20.dp))
    }
}

@Composable
private fun ReportDetailItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    if (value.isNotBlank()) {
        Column(modifier = modifier) {
            Text(
                text = label,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = value,
                fontSize = 15.sp,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
