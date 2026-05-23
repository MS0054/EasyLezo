package am.mojtaba.armengo.admin.ui.screen.metadata.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import am.mojtaba.armengo.core.domain.model.AppLanguages

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppLanguagesSheet(
    metadataAppLanguages: AppLanguages?,
    onDismiss: () -> Unit,
    onSubmit: (AppLanguages) -> Unit
) {
    var from by remember { mutableStateOf(metadataAppLanguages?.from ?: "" ) }
    var to by remember { mutableStateOf(metadataAppLanguages?.to ?: "") }
    var app by remember { mutableStateOf(metadataAppLanguages?.app ?: "") }

    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton (onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null) }
            Text("App Languages", style = MaterialTheme.typography.headlineSmall)
            Button(onClick = {onSubmit ( AppLanguages(from = from, to = to, app = app) ) }) { Text("Save") }
        }
        Spacer(Modifier.size(16.dp))

        DropDown(metadataAppLanguages, from, "From") { from = it }
        DropDown(metadataAppLanguages, to, "To") { to = it }
        DropDown(metadataAppLanguages, app, "App") { app = it }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDown(
    metadataAppLanguages: AppLanguages?,
    language: String,
    label: String,
    onLanguageChange: (String) -> Unit
){

    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    ) {
        OutlinedTextField(
            value = language,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            metadataAppLanguages?.languages?.forEach { language ->
                DropdownMenuItem(
                    text = { Text(text = language.name) },
                    onClick = {
                        onLanguageChange(language.name)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}
