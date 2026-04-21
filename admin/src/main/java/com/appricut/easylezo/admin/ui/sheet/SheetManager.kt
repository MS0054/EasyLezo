package com.appricut.easylezo.admin.ui.sheet

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.appricut.easylezo.admin.ui.RefreshData
import com.appricut.easylezo.admin.ui.UiEvent
import com.appricut.easylezo.admin.ui.screen.admin.sheet.ConfirmSheet
import com.appricut.easylezo.admin.ui.screen.auth.AuthV
import com.appricut.easylezo.admin.ui.screen.category.CategoryV
import com.appricut.easylezo.admin.ui.screen.category.sheet.AddCategorySheet
import com.appricut.easylezo.admin.ui.screen.category.sheet.EditCategorySheet
import com.appricut.easylezo.admin.ui.screen.category.sheet.SortCategorySheet
import com.appricut.easylezo.admin.ui.screen.language.LanguageV
import com.appricut.easylezo.admin.ui.screen.language.sheet.AddLanguageSheet
import com.appricut.easylezo.admin.ui.screen.language.sheet.EditLanguageSheet
import com.appricut.easylezo.admin.ui.screen.language.sheet.SortLanguageSheet
import com.appricut.easylezo.admin.ui.screen.metadata.MetadataV
import com.appricut.easylezo.admin.ui.screen.metadata.sheet.AppLanguagesSheet
import com.appricut.easylezo.admin.ui.screen.metadata.sheet.LastUpdateSheet
import com.appricut.easylezo.admin.ui.screen.metadata.sheet.SettingsSheet
import com.appricut.easylezo.admin.ui.screen.resource.ResourceV
import com.appricut.easylezo.admin.ui.screen.resource.sheet.AddResourceSheet
import com.appricut.easylezo.admin.ui.screen.resource.sheet.EditResourceSheet
import com.appricut.easylezo.admin.ui.screen.sentence.SentenceV
import com.appricut.easylezo.admin.ui.screen.sentence.sheet.AddSentenceSheet
import com.appricut.easylezo.admin.ui.screen.sentence.sheet.EditSentenceSheet
import com.appricut.easylezo.admin.ui.screen.sentence.sheet.SortSentenceSheet
import com.appricut.easylezo.admin.ui.screen.user.UserV
import com.appricut.easylezo.core.domain.model.AppLanguages
import com.appricut.easylezo.core.domain.model.Settings
import kotlinx.coroutines.flow.merge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SheetManager(
    sheetV: SheetV,
    authV: AuthV,
    userV: UserV,
    categoryV: CategoryV,
    sentenceV: SentenceV,
    languageV: LanguageV,
    metadataV: MetadataV,
    resourceV: ResourceV,
    onLogoutSuccess: () -> Unit,
    onRefresh: (RefreshData) -> Unit,
    isSynced: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val currentSheet = sheetV.currentSheet
    var workerTag by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // (event listener)
    LaunchedEffect(Unit) {
        merge(
            languageV.event,
            categoryV.event,
            sentenceV.event,
            userV.event,
            resourceV.event,
            metadataV.event
        ).collect { event ->
            when (event) {
                is UiEvent.Started -> {
                    Log.i("zoooo", "2")
                    sheetV.closeSheet()
                    onRefresh(RefreshData.PROGRESS)
                }
                is UiEvent.SyncStatue -> {
                    Log.i("zoooo", "1: ${event.isSynced}")
                    isSynced(event.isSynced)
                    if (event.isSynced) onRefresh(RefreshData.SYNC) else onRefresh(RefreshData.DONE)
                }
                is UiEvent.StartSync -> {
                    Log.i("zoooo", "3")
                    workerTag = event.workerTag
                }
                is UiEvent.Success -> {
                    Log.i("zoooo", "4")
//                    onRefresh(RefreshData.DONE)
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
                is UiEvent.Error -> {
                    Log.i("zoooo", "5")
                    onRefresh(RefreshData.ERROR)
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    LaunchedEffect(workerTag) {
        Log.i("zoooo", "6")
        if (workerTag.isNotEmpty()) {
            WorkManager.getInstance(context).getWorkInfosByTagFlow(workerTag).collect { workInfos ->
                val workInfo = workInfos.firstOrNull()
                when (workInfo?.state) {
                    WorkInfo.State.RUNNING -> onRefresh(RefreshData.PROGRESS)
                    WorkInfo.State.SUCCEEDED -> { onRefresh(RefreshData.DONE) ; workerTag = "" }
                    WorkInfo.State.FAILED -> { onRefresh(RefreshData.ERROR) ; workerTag = "" }
                    else -> {}
                }
            }
        }
    }


    if (currentSheet != AppSheet.None) {
        ModalBottomSheet(
            onDismissRequest = {
               sheetV.closeSheet()
            },
            sheetState = sheetState,
            modifier = Modifier.fillMaxSize()
        ) {
            when (currentSheet) {

                is AppSheet.Sync -> {
                    ConfirmSheet(
                        title = "Sync",
                        text = "Do you want to sync?",
                        onConfirm = {
                            categoryV.syncCategory()
                            sheetV.closeSheet()
                        },
                        onDismiss = {
                            sheetV.closeSheet()
                        }
                    )
                }
                is AppSheet.Settings -> {
                    val settings = metadataV.metadataSettingsUiState.value.data ?: Settings()
                    SettingsSheet (
                        settings = settings,
                        onDismiss = { sheetV.closeSheet() },
                        onSubmit = {
                            metadataV.updateMetadataSettings(it)
                        }
                    )
                }
                is AppSheet.AddResource -> {
                    AddResourceSheet (
                        onDismiss = { sheetV.closeSheet() },
                        onSubmit = {
                            resourceV.addResource(context,it)
                        }
                    )
                }
                is AppSheet.EditResource->{
                    EditResourceSheet (
                        resource = currentSheet.resource,
                        onSubmit = {
                            resourceV.editResource(it)
                        },
                        onDelete = {
                            resourceV.deleteResource(it)
                        }
                    )
                }
                is AppSheet.AppLanguage ->{
                    val metadataAppLanguages = metadataV.metadataAppLanguagesUiState.value.data ?: AppLanguages()
                    AppLanguagesSheet(
                        metadataAppLanguages = metadataAppLanguages,
                        onDismiss = { sheetV.closeSheet() },
                        onSubmit = {
                            metadataV.updateMetadataAppLanguages(it)
                        }
                    )

                }
                is AppSheet.LastUpdate -> {
                    LastUpdateSheet(
                        onNewLastUpdate = { it
                            metadataV.updateLastUpdate(it)
                        }
                    )
                }
                is AppSheet.LogoutConfirm -> {
                    ConfirmSheet(
                        title = "Logout",
                        text = "Do you want to logout?",
                        onConfirm = {
                            authV.signOut()
                            sheetV.closeSheet()
                            onLogoutSuccess()
                        },
                        onDismiss = { sheetV.closeSheet() }
                    )
                }
                is AppSheet.AddCategory -> {
                    AddCategorySheet(
                        maxOrder = currentSheet.maxOrder,
                        onDismiss = { sheetV.closeSheet() },
                        onSubmit = {
                            categoryV.addCategory(it)
                        }
                    )
                }
                is AppSheet.SortCategory -> {
                    val categories = categoryV.categoryUiState.value.data ?: emptyList()
                    SortCategorySheet (
                        categories = categories,
                        onDismiss = { sheetV.closeSheet() },
                        onSubmit = {
                            categoryV.sortCategory(it)
                        }
                    )
                }
                is AppSheet.EditCategory -> {
                    EditCategorySheet(
                        category = currentSheet.category,
                        onSubmit = {
                            categoryV.updateCategory(it)},
                        onDelete = {
                            categoryV.deleteCategory(it)}
                    )
                }
                is AppSheet.AddSentence -> {
                    val languages = languageV.languageUiState.value.data ?: emptyList()
                    val categoryId = sentenceV.selectedCategoryId.value ?: ""
                    AddSentenceSheet(
                        languages,
                        categoryId,
                        maxOrder = currentSheet.maxOrder,
                        onDismiss = { sheetV.closeSheet() },
                        onSubmit = {
                            sentenceV.addSentence(it)                         }
                    )
                }
                is AppSheet.EditSentence -> {
                    val languages = languageV.languageUiState.value.data ?: emptyList()
                    EditSentenceSheet (
                        languages = languages,
                        sentence = currentSheet.sentence,
                        onDelete = {
                            sentenceV.deleteSentence(it)                         },
                        onSubmit = {
                            sentenceV.updateSentence(it)                         }
                    )
                }
                is AppSheet.SortSentence -> {
                    val sentences = sentenceV.sentenceUiState.value.data ?: emptyList()
                    SortSentenceSheet(
                        sentences = sentences,
                        onDismiss = { sheetV.closeSheet() },
                        onSubmit = {
                            sentenceV.sortSentences(it)                        }
                    )
                }
                    is AppSheet.AddLanguage -> {
                        AddLanguageSheet(
                            maxOrder = currentSheet.maxOrder,
                            onDismiss = { sheetV.closeSheet() },
                            onSubmit = {
                                languageV.addLanguage(it)
                            }
                        )
                    }
                is AppSheet.SortLanguage -> {
                    val languages = languageV.languageUiState.value.data ?: emptyList()
                    SortLanguageSheet (
                        languages = languages,
                        onDismiss = { sheetV.closeSheet() },
                        onSubmit = {
                            languageV.sortLanguage(it)
                        }
                    )
                }
                is AppSheet.EditLanguage -> {
                    EditLanguageSheet (
                        language = currentSheet.language,
                        onSubmit = {
                            languageV.updateLanguage(it)
                        },
                        onDelete = {
                            languageV.deleteLanguage(it)                        }
                    )
                }
                is AppSheet.EditUser -> {
                    EditLanguageSheet (
                        user = currentSheet.user,
                        onSubmit = {
                            userV.updateUser(it)
                        },
                        onDelete = {
//                            scope.launch { userV.deleteUser(it) ; sheetV.closeSheet() }
                        }
                    )
                }
                else -> {}
            }
        }
    }
}