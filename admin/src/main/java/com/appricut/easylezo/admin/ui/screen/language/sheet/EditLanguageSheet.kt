package com.appricut.easylezo.admin.ui.screen.language.sheet

import android.util.Log
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
import com.appricut.easylezo.core.domain.model.Language


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditLanguageSheet(
    language: Language, // زبان ورودی برای ویرایش
    onSubmit: (Language) -> Unit,
    onDelete: (Language) -> Unit
) {

    var name by remember { mutableStateOf(language.name) }
    var code by remember { mutableStateOf(language.code) }
    var flag by remember { mutableStateOf(language.flag) }
    var isFromLanguage by remember { mutableStateOf(language.isFromLanguage) }
    var isToLanguage by remember { mutableStateOf(language.isToLanguage) }


    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Edit Category", style = MaterialTheme.typography.headlineSmall)
            Row {
                IconButton(onClick = { onDelete(language) }) { Icon(Icons.Default.Delete, tint = Color.Red, contentDescription = null) }
                Button(onClick = { onSubmit(language.copy(
                    name = name,
                    code = code,
                    flag = flag,
                    isFromLanguage = isFromLanguage,
                    isToLanguage = isToLanguage,
                    updatedAt = System.currentTimeMillis()
                )) }) { Text("Save") }
            }
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