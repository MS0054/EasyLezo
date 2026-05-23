package am.mojtaba.armengo.admin.ui.screen.language.sheet

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import am.mojtaba.armengo.core.domain.model.Language


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLanguageSheet(
    maxOrder: Int,
    onDismiss: () -> Unit,
    onSubmit: (Language) -> Unit
) {

    var name by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var flag by remember { mutableStateOf("") }
    var isFromLanguage by remember { mutableStateOf(false) }
    var isToLanguage by remember { mutableStateOf(false) }


    Column(Modifier
        .fillMaxWidth()
        .padding(20.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null) }
            Text("Add Language", style = MaterialTheme.typography.headlineSmall)
            Button(onClick = {onSubmit(Language(
                    name = name,
                    code = code,
                    flag = flag,
                    order = maxOrder + 1,
                    isFromLanguage = isFromLanguage,
                    isToLanguage = isToLanguage,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )) }) { Text("Add") }
        }
        Spacer(Modifier.size(16.dp))

        // فیلد نام زبان
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("name") },
            modifier = Modifier.fillMaxWidth()
        )

        // فیلد کد زبان
        OutlinedTextField(
            value = code,
            onValueChange = { code = it },
            label = { Text("code") },
            modifier = Modifier.fillMaxWidth()
        )

        // فیلد پرچم
        OutlinedTextField(
            value = flag,
            onValueChange = { flag = it },
            label = { Text("flag") },
            modifier = Modifier.fillMaxWidth()
        )

        // سوییچ برای Is From Language
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("From")
            Switch(
                checked = isFromLanguage,
                onCheckedChange = { isFromLanguage = it }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("To")
            Switch(
                checked = isToLanguage,
                onCheckedChange = { isToLanguage = it }
            )
        }
    }
}