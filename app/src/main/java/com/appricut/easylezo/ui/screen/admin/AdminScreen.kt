package com.appricut.easylezo.ui.screen.admin

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.appricut.easylezo.data.model.Category
import com.appricut.easylezo.ui.screen.admin.sheet.AddCategoryBottomSheet
import com.appricut.easylezo.ui.screen.admin.sheet.EditCategoryBottomSheet

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(viewModel: AdminViewModel) {

    val categories by viewModel.categories.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    // --- bottom sheet states ---
    val showBottomSheet = remember { mutableStateOf(false) }
    var categoryForEdit by remember { mutableStateOf<Category?>(null) }

    val bottomSheetState = rememberModalBottomSheetState()

    // ---------- BottomSheet UI ----------
    if (showBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet.value = false
                categoryForEdit = null
            },
            sheetState = bottomSheetState
        ) {
            EditCategoryBottomSheet(
                category = categoryForEdit,
                onDismiss = {
                    showBottomSheet.value = false
                    categoryForEdit = null
                },
                onSubmit = { updatedCat ->
                    if (updatedCat.id.isEmpty()) {
                        viewModel.addCategory(updatedCat)
                    } else {
                        viewModel.updateCategory(updatedCat) // اگر update جدا خواستی می‌سازم
                    }
                }
            )
        }
    }

    var showAddSheet by remember { mutableStateOf(false) }
    if (showAddSheet) {
        AddCategoryBottomSheet (
            onDismiss = { showAddSheet = false },
            onSubmit = { newCat ->
                viewModel.addCategory(newCat)
            }
        )
    }

    // ---------- MAIN UI ----------
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Admin Dashboard", style = MaterialTheme.typography.headlineMedium)
            IconButton(
                onClick = {
                    showAddSheet=true
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Profile"
                )
            }
        }
        Spacer(Modifier.height(8.dp))

        if (loading) CircularProgressIndicator()
        error?.let { Text("Error: $it", color = MaterialTheme.colorScheme.error) }

        Spacer(Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(categories) { cat ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .combinedClickable(
                            onClick = {
                                selectedCategory = cat
                                viewModel.selectCategory(cat.id)
                            },
                            onLongClick = {
                                categoryForEdit = cat
                                showBottomSheet.value = true
                            }
                        )
                ) {
                    Row(modifier = Modifier.padding(12.dp)) {
                        if (cat.image.isNotEmpty()) {
                            AsyncImage(
                                model = cat.image,
                                contentDescription = cat.name,
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(Modifier.width(12.dp))
                        }
                        Column {
                            Text(cat.name, style = MaterialTheme.typography.titleLarge)
                            Text("ID: ${cat.id}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }

        selectedCategory?.let { cat ->
            Spacer(Modifier.height(12.dp))
            Text("Sentences in ${cat.name}", style = MaterialTheme.typography.titleMedium)
            // بعداً بخش جملات با BottomSheet نیز اضافه می‌کنم
        }
    }
}
