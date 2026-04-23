package com.appricut.easylezo.admin.ui.screen.category.sheet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.appricut.easylezo.core.domain.model.Category
import com.appricut.easylezo.core.domain.model.Language
import com.appricut.easylezo.core.domain.model.Translate

@Composable
fun EditCategorySheet(
    languages: List<Language>,
    category: Category,
    onSubmit: (Category) -> Unit,
    onDelete: (String) -> Unit
) {

    val translationMap = remember { mutableStateMapOf<String, String>().apply {
        category.translations.forEach { put(it.language, it.text) }
    } }
    var image by remember { mutableStateOf(category.image) }

    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        Row (Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Edit Category", style = MaterialTheme.typography.headlineSmall)
            Row {
                IconButton (onClick = { onDelete(category.id)}) { Icon(Icons.Default.Delete, tint = Color.Red, contentDescription = null) }
                Button(onClick = {
                    val updatedTranslations = translationMap.map { (code, text) -> Translate(language = code, text = text) }
                    onSubmit(category.copy(
                        id = category.id,
                        image = image,
                        updatedAt = System.currentTimeMillis(),
                        translations = updatedTranslations
                    )) }) { Text("Save") }
            }
        }
        Spacer(Modifier.size(16.dp))

        LazyColumn (
            modifier = Modifier.weight(1f, fill = false),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                OutlinedTextField(
                    value = image,
                    onValueChange = { image = it },
                    label = { Text("Image URL") },
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
