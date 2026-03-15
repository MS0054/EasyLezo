package com.appricut.easylezo.ui.screen.admin

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appricut.easylezo.core.domain.model.Category
import com.appricut.easylezo.core.domain.model.Sentence
import com.appricut.easylezo.ui.screen.admin.sheet.AddCategoryBottomSheet
import com.appricut.easylezo.ui.screen.admin.sheet.AddSentenceBottomSheet
import com.appricut.easylezo.ui.screen.admin.sheet.ConfirmBottomSheet
import com.appricut.easylezo.ui.screen.admin.sheet.EditCategoryBottomSheet
import com.appricut.easylezo.ui.screen.admin.sheet.EditSentenceBottomSheet
import com.appricut.easylezo.ui.screen.admin.sheet.SortCategorySheet
import com.appricut.easylezo.ui.screen.admin.sheet.SortSentenceSheet
import com.appricut.easylezo.ui.screen.auth.AuthViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    adminViewModel: AdminViewModel,
    authViewModel: AuthViewModel,
    onLogout: () -> Unit
) {

    val categories by adminViewModel.categories.collectAsState()
    val sentences by adminViewModel.sentences.collectAsState()
    val loading by adminViewModel.loading.collectAsState()
    val error by adminViewModel.error.collectAsState()

    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    // --- bottom sheet states ---
    val bottomSheetState = rememberModalBottomSheetState()
    var showEditCategorySheet by remember { mutableStateOf(false) }
    var showAddSheet by remember { mutableStateOf(false) }
    var showLogoutSheet by remember { mutableStateOf(false) }
    var showSortCategorySheet by remember { mutableStateOf(false) }
    var categoryForEdit by remember { mutableStateOf<Category?>(null) }



    // ---------- BottomSheet UI ----------
    if (showEditCategorySheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showEditCategorySheet = false
                categoryForEdit = null
            },
            sheetState = bottomSheetState
        ) {
            EditCategoryBottomSheet(
                category = categoryForEdit,
                onDismiss = {
                    showEditCategorySheet = false
                    categoryForEdit = null
                },
                onSubmit = { updatedCat ->
                    if (updatedCat.id.isEmpty()) {
                        adminViewModel.addCategory(updatedCat)
                    } else {
                        adminViewModel.updateCategory(updatedCat) // اگر update جدا خواستی می‌سازم
                    }
                }
            )
        }
    }


    if (showAddSheet) {
        AddCategoryBottomSheet(
            onDismiss = { showAddSheet = false },
            onSubmit = { newCat ->
                adminViewModel.addCategory(newCat)
            }
        )
    }

    if (showLogoutSheet) {
        ConfirmBottomSheet(
            title = "Logout",
            text = "Do you want to logout?",
            onDismiss = { showLogoutSheet = false },
            onConfirm = {
                authViewModel.signOut()
                onLogout()
            }
        )
    }

    if (showSortCategorySheet){
        SortCategorySheet (
            categories = categories,
            onDismiss = { showSortCategorySheet = false },
            onSave = { sorted ->
                adminViewModel.saveCategoryOrder(sorted)
                showSortCategorySheet = false
            }
        )
    }

    // ---------- MAIN UI ----------
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Category", style = MaterialTheme.typography.headlineMedium)
            Row {
                IconButton(
                    modifier = Modifier.size(52.dp),
                    onClick = {
                        showLogoutSheet = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ExitToApp,
                        contentDescription = "Logout"
                    )
                }
                IconButton(
                    modifier = Modifier.size(52.dp),
                    onClick = {
                        showSortCategorySheet = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.List,
                        contentDescription = "SortCategory"
                    )
                }
                IconButton(
                    modifier = Modifier.size(52.dp),
                    onClick = {
                        showAddSheet = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "AddCategory"
                    )
                }

            }
        }
        Spacer(Modifier.height(8.dp))

        if (loading) CircularProgressIndicator()
        error?.let { Text("Error: $it", color = MaterialTheme.colorScheme.error) }

        Spacer(Modifier.height(8.dp))

        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(categories) { cat ->
                Card(
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .combinedClickable(
                            onClick = {
                                selectedCategory = cat
                                adminViewModel.selectCategory(cat.id)
                            },
                            onLongClick = {
                                categoryForEdit = cat
                                showEditCategorySheet = true
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

        selectedCategory?.let { cat ->
            Spacer(Modifier.height(12.dp))
            SentenceList(cat.id, adminViewModel, sentences)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SentenceList(
    catId: String,
    adminViewModel: AdminViewModel,
    sentences: List<Sentence>,
    isAdmin: Boolean = false
) {

    // --- bottom sheet states ---
    var showEditSentenceSheet by remember { mutableStateOf(false) }
    var showAddSentenceSheet by remember { mutableStateOf(false) }
    var showSortSentenceSheet by remember { mutableStateOf(false) }
    var sentenceForEdit by remember { mutableStateOf<Sentence?>(null) }

    val bottomSheetState = rememberModalBottomSheetState()

    // ---------- BottomSheet UI ----------
    if (showEditSentenceSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showEditSentenceSheet = false
                sentenceForEdit = null
            },
            sheetState = bottomSheetState
        ) {
            EditSentenceBottomSheet(
                sentence = sentenceForEdit,
                onDismiss = {
                    showEditSentenceSheet = false
                    sentenceForEdit = null
                },
                onDelete = {
                    showEditSentenceSheet = false
                    adminViewModel.deleteSentence(catId, sentenceForEdit?.id ?: "")
                },

                onSubmit = { updatedSentence ->
                    if (updatedSentence.id.isEmpty()) {
                        adminViewModel.addSentence(catId, updatedSentence)
                    } else {
                        adminViewModel.updateSentence(
                            catId,
                            updatedSentence
                        ) // اگر update جدا خواستی می‌سازم
                    }
                }
            )
        }
    }


    if (showAddSentenceSheet) {
        AddSentenceBottomSheet (
            onDismiss = { showAddSentenceSheet = false },
            onSubmit = { newSentence ->
                adminViewModel.addSentence(catId, newSentence)
            }
        )
    }
    if (showSortSentenceSheet){
        SortSentenceSheet (
            sentences = sentences,
            onDismiss = { showSortSentenceSheet = false },
            onSave = { sorted ->
                adminViewModel.saveSentenceOrder(catId, sorted)
                showSortSentenceSheet = false
            }
        )
    }


// ---------- MAIN UI ----------
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Sentence", style = MaterialTheme.typography.headlineMedium)
            Row {
                IconButton(onClick = { showSortSentenceSheet = true }) {
                    Icon(imageVector = Icons.Filled.List, contentDescription = "SortSentence")
                }
                IconButton(onClick = { showAddSentenceSheet = true }) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "AddSentence")
                }
            }
        }
        Spacer(Modifier.height(8.dp))


        if (sentences.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                itemsIndexed(sentences) { index, sentence ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp)
                            .combinedClickable(
                                onClick = {
                                    sentenceForEdit = sentence
                                    showEditSentenceSheet = true
//                                    selectedCategory = cat
//                                    viewModel.selectCategory(cat.id)
                                },
                                onLongClick = {
//                                    categoryForEdit = cat
//                                    showBottomSheet.value = true
                                }
                            )
                    ) {
                        Row {
                            Text(index.toString(), textAlign = TextAlign.Center, modifier = Modifier.background(
                                Color.LightGray, shape = CircleShape
                            ).padding( 12.dp, 24.dp), fontSize = 20.sp, style = MaterialTheme.typography.bodySmall)
                            Column(modifier = Modifier.padding(12.dp,10.dp)) {
                                Text(sentence.ar, fontSize = 16.sp, style = MaterialTheme.typography.titleLarge)
                                Text(sentence.fa, fontSize = 13.sp, style = MaterialTheme.typography.titleLarge)
                            }
                        }
                    }
                }
            }
        }
    }
}
