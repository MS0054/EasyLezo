package com.appricut.easylezo.admin.ui.screen.category.sheet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.appricut.easylezo.core.domain.model.Category
import com.appricut.easylezo.core.domain.model.Language
import com.appricut.easylezo.core.domain.model.Translate
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategorySheet(
    languages: List<Language>,
    maxOrder: Int,
    onDismiss: () -> Unit,
    onSubmit: (Category) -> Unit
) {
    val translationMap = remember { mutableStateMapOf<String, String>() }
    var imageUrl by remember { mutableStateOf("") }

    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton (onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null) }
            Text("Add Category", style = MaterialTheme.typography.headlineSmall)
            Button(onClick = {
                val translations = translationMap.map { (code, text) -> Translate(language = code, text = text) }
                    onSubmit(Category(
                        id = UUID.randomUUID().toString(),
                        image = imageUrl.trim(),
                        order = maxOrder + 1,
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis(),
                        translations = translations
                    )) },) { Text("Add") }
        }
        Spacer(Modifier.size(16.dp))


        LazyColumn (
            modifier = Modifier.weight(1f, fill = false),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("Image URL (optional)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            items(languages) { language ->
                OutlinedTextField(
                    value = translationMap[language.name] ?: "",
                    onValueChange = { translationMap[language.name] = it },
                    label = { Text(language.name) },
                    placeholder = { Text(language.name) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { AsyncImage( model = language.flag, contentDescription = null, modifier = Modifier.size(24.dp) ) }
                )
            }
        }




    }

}
