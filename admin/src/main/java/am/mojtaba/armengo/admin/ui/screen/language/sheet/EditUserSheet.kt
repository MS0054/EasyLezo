package am.mojtaba.armengo.admin.ui.screen.language.sheet

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import am.mojtaba.armengo.core.domain.model.User


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditLanguageSheet(
    user: User,
    onSubmit: (User) -> Unit,
    onDelete: (User) -> Unit
) {
    var uid by remember { mutableStateOf(user.uid) }
    var displayName by remember { mutableStateOf(user.displayName) }
    var email by remember { mutableStateOf(user.email) }
    var appLanguages by remember { mutableStateOf(user.appLanguages) }
    var isAdmin by remember { mutableStateOf(user.role == "admin") }


    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Edit User", style = MaterialTheme.typography.headlineSmall)
            Row {
                IconButton(onClick = { onDelete(user) }) { Icon(Icons.Default.Delete, tint = Color.Red, contentDescription = null) }
                Button(onClick = { onSubmit(user.copy(displayName = displayName, role = if(isAdmin) "admin" else "user" ))}) { Text("Save") }
            }
        }
        Spacer(Modifier.size(16.dp))


        OutlinedTextField(
            value = uid,
            onValueChange = { uid = it },
            label = { Text("uid") },
            modifier = Modifier.fillMaxWidth()
        )


        OutlinedTextField(
            value = displayName,
            onValueChange = { displayName = it },
            label = { Text("name") },
            modifier = Modifier.fillMaxWidth()
        )


        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = appLanguages.toString(),
            onValueChange = {  },
            label = { Text("appLanguages") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("isAdmin")
            Switch(
                checked = isAdmin,
                onCheckedChange = { isAdmin = it }
            )
        }
    }
}