package am.mojtaba.armengo.admin.ui.sheet

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
import am.mojtaba.armengo.admin.ui.RefreshData
import am.mojtaba.armengo.admin.ui.UiEvent
import am.mojtaba.armengo.admin.ui.screen.admin.sheet.ConfirmSheet
import am.mojtaba.armengo.admin.ui.screen.auth.AuthV
import am.mojtaba.armengo.admin.ui.screen.category.CategoryV
import am.mojtaba.armengo.admin.ui.screen.category.sheet.AddCategorySheet
import am.mojtaba.armengo.admin.ui.screen.category.sheet.EditCategorySheet
import am.mojtaba.armengo.admin.ui.screen.category.sheet.SortCategorySheet
import am.mojtaba.armengo.admin.ui.screen.language.LanguageV
import am.mojtaba.armengo.admin.ui.screen.language.sheet.AddLanguageSheet
import am.mojtaba.armengo.admin.ui.screen.language.sheet.EditLanguageSheet
import am.mojtaba.armengo.admin.ui.screen.language.sheet.SortLanguageSheet
import am.mojtaba.armengo.admin.ui.screen.metadata.MetadataV
import am.mojtaba.armengo.admin.ui.screen.metadata.sheet.AppLanguagesSheet
import am.mojtaba.armengo.admin.ui.screen.metadata.sheet.LastUpdateSheet
import am.mojtaba.armengo.admin.ui.screen.metadata.sheet.SettingsSheet
import am.mojtaba.armengo.admin.ui.screen.metadata.sheet.SyncSheet
import am.mojtaba.armengo.admin.ui.screen.metadata.sheet.UpdateInfoSheet
import am.mojtaba.armengo.admin.ui.screen.resource.ResourceV
import am.mojtaba.armengo.admin.ui.screen.resource.sheet.AddResourceSheet
import am.mojtaba.armengo.admin.ui.screen.resource.sheet.EditResourceSheet
import am.mojtaba.armengo.admin.ui.screen.sentence.SentenceV
import am.mojtaba.armengo.admin.ui.screen.sentence.sheet.AddSentenceSheet
import am.mojtaba.armengo.admin.ui.screen.sentence.sheet.EditSentenceSheet
import am.mojtaba.armengo.admin.ui.screen.sentence.sheet.SortSentenceSheet
import am.mojtaba.armengo.admin.ui.screen.user.UserV
import am.mojtaba.armengo.core.domain.model.AppLanguages
import am.mojtaba.armengo.core.domain.model.Settings
import am.mojtaba.armengo.core.domain.model.UpdateInfo
import android.util.Log
import kotlinx.coroutines.delay
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
    isSyncNeeded: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val currentSheet = sheetV.currentSheet
    var workerTag by remember { mutableStateOf("") }
    var refreshStatus by remember { mutableStateOf(RefreshData.DONE) }
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
                    sheetV.closeSheet()
                    refreshStatus = RefreshData.PROGRESS
                }
                is UiEvent.SyncStatue -> {
                    isSyncNeeded(event.isSyncNeeded)
                    if (event.isSyncNeeded) {
                        refreshStatus = RefreshData.SYNC
                    }
                }
                is UiEvent.StartSync -> {
                    workerTag = event.workerTag
                }
                is UiEvent.Success -> {
                    refreshStatus = RefreshData.DONE
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
                is UiEvent.Error -> {
                    refreshStatus = RefreshData.ERROR
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
            }
            onRefresh(refreshStatus)
        }
    }

    LaunchedEffect(workerTag) {
        if (workerTag.isNotEmpty()) {
            WorkManager.getInstance(context).getWorkInfosByTagFlow(workerTag).collect { workInfos ->
                val workInfo = workInfos.firstOrNull()
                when (workInfo?.state) {
                    WorkInfo.State.RUNNING -> onRefresh(RefreshData.PROGRESS)
                    WorkInfo.State.SUCCEEDED -> { onRefresh(RefreshData.DONE) ;  }
                    WorkInfo.State.FAILED -> { onRefresh(RefreshData.ERROR) ; }
                    else -> {}
                }
            }
        }
    }


    if (currentSheet != AppSheet.None) {
        if ( // half size BottomSheet
            currentSheet == AppSheet.Sync ||
            currentSheet == AppSheet.Logout
        ) {
            ModalBottomSheet(onDismissRequest = { sheetV.closeSheet() }, sheetState = sheetState,) {
                when (currentSheet) {

                    is AppSheet.Sync -> {
                        val isExistUnSyncedUser = false
                        val isExistUnSyncedCategory = categoryV.unsyncedCategoryUiState.value.data ?: false
                        val isExistUnSyncedSentence = false
                        val isExistUnSyncedLanguage = languageV.unsyncedLanguageUiState.value.data ?: false

                        SyncSheet(
                            isExistUnSyncedUser,
                            isExistUnSyncedCategory,
                            isExistUnSyncedSentence,
                            isExistUnSyncedLanguage,
                            onConfirm = {
                                ConfirmSheet(
                                    title = "Confirm",
                                    text = "Do you want to confirm $it changes?",
                                    onConfirm = {
                                        when (it) {
                                            "User" -> ""
                                            "Category" -> categoryV.syncCategoryToServer()
                                            "Sentence" -> sentenceV.syncSentenceToServer()
                                            "Language" -> languageV.syncLanguageToServer()
                                        }
                                        sheetV.closeSheet()
                                    },
                                    onDismiss = { sheetV.closeSheet() }
                                )
                            },
                            onReject = {
                                ConfirmSheet(
                                    title = "Reject",
                                    text = "Do you want to reject $it changes?",
                                    onConfirm = {
                                        when (it) {
                                            "User" -> ""
                                            "Category" -> categoryV.rejectCategoryChanges()
                                            "Sentence" -> sentenceV.rejectSentenceChanges()
                                            "Language" -> languageV.rejectLanguageChanges()
                                        }
                                        sheetV.closeSheet()
                                    },
                                    onDismiss = { sheetV.closeSheet() }
                                )
                            }
                        )

                    }

                    is AppSheet.Logout -> {
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



                    else -> {}
                }

            }
        } else { // full size BottomSheet
            ModalBottomSheet(
                onDismissRequest = {
                    sheetV.closeSheet()
                },
                sheetState = sheetState,
                modifier = Modifier.fillMaxSize()
            ) {
                when (currentSheet) {

                    is AppSheet.UpdateInfo -> {
                        val updateInfo =
                            metadataV.metadataUpdateInfoUiState.value.data ?: UpdateInfo()
                        UpdateInfoSheet(
                            updateInfo = updateInfo,
                            onDismiss = { sheetV.closeSheet() },
                            onSubmit = {
                                metadataV.updateMetadataUpdateInfo(it)
                            }
                        )
                    }

                    is AppSheet.Settings -> {
                        val settings = metadataV.metadataSettingsUiState.value.data ?: Settings()
                        SettingsSheet(
                            settings = settings,
                            onDismiss = { sheetV.closeSheet() },
                            onSubmit = {
                                metadataV.updateMetadataSettings(it)
                            }
                        )
                    }

                    is AppSheet.AddResource -> {
                        AddResourceSheet(
                            onDismiss = { sheetV.closeSheet() },
                            onSubmit = {
                                resourceV.addResource(context, it)
                            }
                        )
                    }

                    is AppSheet.EditResource -> {
                        EditResourceSheet(
                            resource = currentSheet.resource,
                            onSubmit = {
                                resourceV.editResource(it)
                            },
                            onDelete = {
                                resourceV.deleteResource(it)
                            }
                        )
                    }

                    is AppSheet.AppLanguage -> {
                        val metadataAppLanguages =
                            metadataV.metadataAppLanguagesUiState.value.data ?: AppLanguages()
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
                            onNewLastUpdate = {
                                it
                                metadataV.updateLastUpdate(it)
                            }
                        )
                    }

                    is AppSheet.AddCategory -> {
                        val languages = languageV.languageUiState.value.data ?: emptyList()
                        AddCategorySheet(
                            languages = languages,
                            maxOrder = currentSheet.maxOrder,
                            onDismiss = { sheetV.closeSheet() },
                            onSubmit = {
                                categoryV.addCategory(it)
                            }
                        )
                    }

                    is AppSheet.SortCategory -> {
                        val categories = categoryV.categoryUiState.value.data ?: emptyList()
                        SortCategorySheet(
                            categories = categories,
                            onDismiss = { sheetV.closeSheet() },
                            onSubmit = {
                                categoryV.sortCategory(it)
                            }
                        )
                    }

                    is AppSheet.EditCategory -> {
                        val languages = languageV.languageUiState.value.data ?: emptyList()
                        EditCategorySheet(
                            languages = languages,
                            category = currentSheet.category,
                            onSubmit = {
                                categoryV.updateCategory(it)
                            },
                            onDelete = {
                                categoryV.deleteCategory(it)
                            }
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
                                sentenceV.addSentence(it)
                            }
                        )
                    }

                    is AppSheet.EditSentence -> {
                        val languages = languageV.languageUiState.value.data ?: emptyList()
                        EditSentenceSheet(
                            languages = languages,
                            sentence = currentSheet.sentence,
                            onDelete = {
                                sentenceV.deleteSentence(it)
                            },
                            onSubmit = {
                                sentenceV.updateSentence(it)
                            }
                        )
                    }

                    is AppSheet.SortSentence -> {
                        val sentences = sentenceV.sentenceUiState.value.data ?: emptyList()
                        SortSentenceSheet(
                            sentences = sentences,
                            onDismiss = { sheetV.closeSheet() },
                            onSubmit = {
                                sentenceV.sortSentences(it)
                            }
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
                        SortLanguageSheet(
                            languages = languages,
                            onDismiss = { sheetV.closeSheet() },
                            onSubmit = {
                                languageV.sortLanguage(it)
                            }
                        )
                    }

                    is AppSheet.EditLanguage -> {
                        EditLanguageSheet(
                            language = currentSheet.language,
                            onSubmit = {
                                languageV.updateLanguage(it)
                            },
                            onDelete = {
                                languageV.deleteLanguage(it)
                            }
                        )
                    }

                    is AppSheet.EditUser -> {
                        EditLanguageSheet(
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
}