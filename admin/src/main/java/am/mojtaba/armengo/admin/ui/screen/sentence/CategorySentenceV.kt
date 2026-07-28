package am.mojtaba.armengo.admin.ui.screen.sentence

import am.mojtaba.armengo.admin.ui.UiState
import am.mojtaba.armengo.admin.ui.screen.BaseViewModel
import am.mojtaba.armengo.core.domain.model.Sentence
import am.mojtaba.armengo.core.domain.usecase.sentence.GetCategorySentencesUseCase
import am.mojtaba.armengo.core.domain.usecase.sentence.ObserveUnSyncedCategorySentenceUseCase
import am.mojtaba.armengo.core.domain.usecase.sentence.SortCategorySentenceUseCase
import am.mojtaba.armengo.core.domain.usecase.sentence.SyncCategorySentenceToServerUseCase
import am.mojtaba.armengo.core.domain.usecase.sentence.UpdateCategorySentencesUseCase
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategorySentenceV @Inject constructor(
    private val getCategorySentencesUseCase: GetCategorySentencesUseCase,
    private val updateCategorySentencesUseCase: UpdateCategorySentencesUseCase,
    private val syncCategorySentenceToServerUseCase: SyncCategorySentenceToServerUseCase,
    private val sortCategorySentenceUseCase: SortCategorySentenceUseCase,
    private val observeUnSyncedCategorySentenceUseCase: ObserveUnSyncedCategorySentenceUseCase
) : BaseViewModel() {

    private val _categorySentencesUiState = MutableStateFlow(UiState<List<Sentence>>())
    val categorySentencesUiState: StateFlow<UiState<List<Sentence>>> = _categorySentencesUiState.asStateFlow()

    private val _selectedCategoryId = MutableStateFlow<String?>(null)
    val selectedCategoryId: StateFlow<String?> = _selectedCategoryId.asStateFlow()

    private val _unsyncedCategorySentenceState = MutableStateFlow(false)
    val unsyncedCategorySentenceState: StateFlow<Boolean> = _unsyncedCategorySentenceState.asStateFlow()


    init {
        observeSyncStatus()
    }

    fun observeCategorySentences(categoryId: String) {
        _selectedCategoryId.value = categoryId
        viewModelScope.launch {
            getCategorySentencesUseCase(categoryId)
                .onStart { _categorySentencesUiState.value = UiState(isLoading = true) }
                .catch { e ->
                    _categorySentencesUiState.value = UiState(error = e.message ?: "Unknown error")
                }
                .collect { sentences ->
                    _categorySentencesUiState.value = UiState(data = sentences)
                    observeSyncStatus()
                }
        }
    }


    fun observeSyncStatus() {
        viewModelScope.launch {
            observeUnSyncedCategorySentenceUseCase().collect { isSyncNeeded ->
                isSyncNeeded(isSyncNeeded)
                _unsyncedCategorySentenceState.value = isSyncNeeded
            }
        }
    }

    fun syncSentenceToServer(workerTag: String = "sync_category_sentence") {
        launchSyncWithEvent(
            action = { syncCategorySentenceToServerUseCase(workerTag) },
            workerTag = workerTag,
            successMessage = "Category Sentences Synced To Server"
        )
    }
//
//    fun rejectSentenceChanges() {
//        launchWithEvent(
//            action = { syncSentenceFromServerUseCase(true) },
//            successMessage = "Rejected Changes"
//        )
//    }

    fun updateCategorySentences(categoryId: String, selectedSentenceIds: List<String>) {
        launchWithEvent(
            action = { updateCategorySentencesUseCase(categoryId, selectedSentenceIds) },
            successMessage = "Sentences updated for category"
        )
    }

    fun sortCategorySentences(categoryId: String ,sortedSentenceIds: List<String>) {
        launchWithEvent(
            action = { sortCategorySentenceUseCase(categoryId, sortedSentenceIds) },
            successMessage = "Sorted"
        )
    }

}