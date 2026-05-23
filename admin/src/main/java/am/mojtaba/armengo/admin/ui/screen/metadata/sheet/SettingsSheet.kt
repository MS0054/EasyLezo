package am.mojtaba.armengo.admin.ui.screen.metadata.sheet

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import am.mojtaba.armengo.core.domain.model.Settings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsSheet(
    settings: Settings,
    onDismiss: () -> Unit,
    onSubmit: (Settings) -> Unit
) {
    var lastVersion by remember { mutableStateOf(settings.lastVersion) }
    var policyUrl by remember { mutableStateOf(settings.policyUrl) }
    var aboutUrl by remember { mutableStateOf(settings.aboutUrl) }
    var termsUrl by remember { mutableStateOf(settings.termsUrl) }



    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton (onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null) }
            Text("Settings", style = MaterialTheme.typography.headlineSmall)
            Button(onClick = { onSubmit(Settings( lastVersion = lastVersion, policyUrl = policyUrl, aboutUrl = aboutUrl, termsUrl = termsUrl )) }) { Text("Save") }
        }
        Spacer(Modifier.size(16.dp))

        OutlinedTextField(
            value = lastVersion,
            onValueChange = { lastVersion = it },
            label = { Text("lastVersion") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = policyUrl,
            onValueChange = { policyUrl = it },
            label = { Text("policyUrl") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = termsUrl,
            onValueChange = { termsUrl = it },
            label = { Text("termsUrl") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = aboutUrl,
            onValueChange = { aboutUrl = it },
            label = { Text("aboutUrl") },
            modifier = Modifier.fillMaxWidth()
        )
    }

}
