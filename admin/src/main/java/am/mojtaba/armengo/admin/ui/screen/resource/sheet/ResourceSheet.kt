//package am.mojtaba.armengo.admin.ui.screen.admin.sheet
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Close
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import am.mojtaba.armengo.core.domain.model.AppLanguages
//import am.mojtaba.armengo.core.domain.model.Resource
//import am.mojtaba.armengo.core.domain.model.Settings
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ResourceSheet(
//    resource: Resource,
//    onDismiss: () -> Unit,
//    onSubmit: (Settings) -> Unit
//) {
//    var lastVersion by remember { mutableStateOf(resource.) }
//
//
//    Column(Modifier.fillMaxWidth().padding(20.dp)) {
//        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//            IconButton (onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null) }
//            Text("Settings", style = MaterialTheme.typography.headlineSmall)
//            Button(onClick = { onSubmit(Settings( lastVersion = lastVersion )) }) { Text("Save") }
//        }
//        Spacer(Modifier.size(16.dp))
//
//        OutlinedTextField(
//            value = lastVersion,
//            onValueChange = { lastVersion = it },
//            label = { Text("LastVersion") },
//            modifier = Modifier.fillMaxWidth()
//        )
//    }
//
//}
