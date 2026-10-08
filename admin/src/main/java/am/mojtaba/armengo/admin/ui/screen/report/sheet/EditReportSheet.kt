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
    var reportType by remember { mutableStateOf(report.reportType) }
    var reportMessageKey by remember { mutableStateOf(report.reportMessageKey) }
    var reportStatusTypeKey by remember { mutableStateOf(report.reportStatusType.key) }
    var itemId by remember { mutableStateOf(report.itemId) }
    var userComment by remember { mutableStateOf(report.userComment) }
    var appVersion by remember { mutableStateOf(report.appVersion) }
    var deviceInfo by remember { mutableStateOf(report.deviceInfo) }
    var userAppLanguage by remember { mutableStateOf(report.userAppLanguage) }

    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Report Detail", style = MaterialTheme.typography.headlineSmall)
            Row {
                IconButton (onClick = { onDelete(report) }) { Icon(Icons.Default.Delete, tint = Color.Red, contentDescription = "Delete") }
                Button(onClick = { onSubmit(report.copy(
                    reportType = reportType,
                    reportMessageKey = reportMessageKey,
                    reportStatusType = report.reportStatusType.copy(key = reportStatusTypeKey),
                    itemId = itemId,
                    userComment = userComment,
                    appVersion = appVersion,
                    deviceInfo = deviceInfo,
                    userAppLanguage = userAppLanguage
                )) }) { Text("Save") }
            }
        }
        Spacer(Modifier.size(16.dp))

        OutlinedTextField(
            value = reportType,
            onValueChange = { reportType = it },
            label = { Text("Report Type") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = reportMessageKey,
            onValueChange = { reportMessageKey = it },
            label = { Text("Report Message Key") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = reportStatusTypeKey,
            onValueChange = { reportStatusTypeKey = it },
            label = { Text("Report Status Type Key") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = itemId,
            onValueChange = { itemId = it },
            label = { Text("Item ID") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = userComment,
            onValueChange = { userComment = it },
            label = { Text("User Comment") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = appVersion,
            onValueChange = { appVersion = it },
            label = { Text("App Version") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = deviceInfo,
            onValueChange = { deviceInfo = it },
            label = { Text("Device Info") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = userAppLanguage,
            onValueChange = { userAppLanguage = it },
            label = { Text("User App Language") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
