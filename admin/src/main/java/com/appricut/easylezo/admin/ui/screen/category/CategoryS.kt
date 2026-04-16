package com.appricut.easylezo.admin.ui.screen.category

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appricut.easylezo.core.domain.model.Category

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CategoryS(
    categoryV: CategoryV,
    onEdit: (Category) -> Unit,
    onAdd: (Int) -> Unit,
    openSentences: (String) -> Unit
) {
    val categoriesUiState by categoryV.categoryUiState.collectAsState()
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
            categoriesUiState.isLoading -> CircularProgressIndicator()
            categoriesUiState.error != null -> {
                Text("Error: ${categoriesUiState.error}", color = MaterialTheme.colorScheme.error)
            }

            else -> {
                val categories = categoriesUiState.data ?: emptyList()
                maxOrder = categories.size
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(categories) { cat ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .combinedClickable(
                                        onClick = {
                                            openSentences(cat.id)
                                        },
                                        onLongClick = {
                                            onEdit(cat)
                                        }
                                    )
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        cat.name,
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


