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
    var reportMessageKey by remember { mutableStateOf("") }
    var itemId by remember { mutableStateOf("") }
    var userComment by remember { mutableStateOf("") }
    var appVersion by remember { mutableStateOf("1.0.0") }
    var deviceInfo by remember { mutableStateOf("") }
    var userAppLanguage by remember { mutableStateOf("en") }

    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton (onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null) }
            Text("Add Report", style = MaterialTheme.typography.headlineSmall)
            Button(onClick = {
                onSubmit(Report(
                    reportMessageKey = reportMessageKey,
                    itemId = itemId,
                    userComment = userComment,
                    appVersion = appVersion,
                    deviceInfo = deviceInfo,
                    userAppLanguage = userAppLanguage
                ))
            }) { Text("Add") }
        }
        Spacer(Modifier.size(16.dp))

        OutlinedTextField(
            value = reportMessageKey,
            onValueChange = { reportMessageKey = it },
            label = { Text("Report Message Key") },
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
