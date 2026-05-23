package am.mojtaba.armengo.admin.ui.screen.metadata.sheet

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import am.mojtaba.armengo.core.domain.model.UpdateInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateInfoSheet(
    updateInfo: UpdateInfo,
    onDismiss: () -> Unit,
    onSubmit: (UpdateInfo) -> Unit
) {
    var minVersion by remember { mutableStateOf(updateInfo.minVersion) }
    var minVersionCode by remember { mutableStateOf(updateInfo.minVersionCode.toString()) }
    var latestVersion by remember { mutableStateOf(updateInfo.latestVersion) }
    var latestVersionCode by remember { mutableStateOf(updateInfo.latestVersionCode.toString()) }
    var forcedVersions by remember { mutableStateOf(updateInfo.forcedVersions) }
    var updateUrl by remember { mutableStateOf(updateInfo.updateUrl) }
    var releaseNotes by remember { mutableStateOf(updateInfo.releaseNotes) }


    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton (onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null) }
            Text("Settings", style = MaterialTheme.typography.headlineSmall)
            Button(onClick = { onSubmit(UpdateInfo(
                minVersion = minVersion,
                minVersionCode = minVersionCode.toIntOrNull() ?: 0,
                latestVersion = latestVersion,
                latestVersionCode = latestVersionCode.toIntOrNull() ?: 0,
                forcedVersions = forcedVersions,
                updateUrl = updateUrl,
                releaseNotes = releaseNotes
            )) }) { Text("Save") }
        }
        Spacer(Modifier.size(16.dp))

        OutlinedTextField(
            value = minVersion,
            onValueChange = { minVersion = it },
            label = { Text("minVersion") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.size(16.dp))
        OutlinedTextField(
            value = minVersionCode,
            onValueChange = { minVersionCode = it },
            label = { Text("minVersionCode") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.size(16.dp))
        OutlinedTextField(
            value = latestVersion,
            onValueChange = { latestVersion = it },
            label = { Text("LastVersion") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.size(16.dp))
        OutlinedTextField(
            value = latestVersionCode,
            onValueChange = { latestVersionCode = it },
            label = { Text("latestVersionCode") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.size(16.dp))
        OutlinedTextField(
            value = forcedVersions,
            onValueChange = { forcedVersions = it },
            label = { Text("forcedVersions") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.size(16.dp))
        OutlinedTextField(
            value = updateUrl,
            onValueChange = { updateUrl = it },
            label = { Text("updateUrl") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.size(16.dp))
        OutlinedTextField(
            value = releaseNotes,
            onValueChange = { releaseNotes = it },
            label = { Text("releaseNotes") },
            modifier = Modifier.fillMaxWidth()
        )
    }

}
