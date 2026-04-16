package com.appricut.easylezo.admin.ui.screen.language

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appricut.easylezo.core.domain.model.Language

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LanguageS(
    languageV: LanguageV,
    onEdit: (Language) -> Unit,
    onAdd: (Int) -> Unit
) {
    val languageUiState by languageV.languageUiState.collectAsState()
    var maxOrder by remember { mutableIntStateOf(0) }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAdd(maxOrder) }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Language"
                )
            }
        }
    ) { paddingValues ->

        when {
            languageUiState.isLoading -> CircularProgressIndicator()

            languageUiState.error != null -> {
                Text(
                    "Error: ${languageUiState.error}",
                    color = MaterialTheme.colorScheme.error
                )
            }

            else -> {
                val languages = languageUiState.data ?: emptyList()
                maxOrder = languages.size
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(languages) { language ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .combinedClickable(
                                        onClick = {
                                            onEdit(language)
                                        },
                                        onLongClick = {

                                        }
                                    )
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        language.name,
                                        fontSize = 16.sp,
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}