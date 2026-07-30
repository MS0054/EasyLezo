package am.mojtaba.armengo.admin.ui.screen.word

import am.mojtaba.armengo.admin.ui.UiState
import am.mojtaba.armengo.admin.ui.screen.BaseViewModel
import am.mojtaba.armengo.core.domain.model.Word
import am.mojtaba.armengo.core.domain.usecase.word.GetCategoryWordsUseCase
import am.mojtaba.armengo.core.domain.usecase.word.ObserveUnSyncedCategoryWordUseCase
import am.mojtaba.armengo.core.domain.usecase.word.SortCategoryWordUseCase
import am.mojtaba.armengo.core.domain.usecase.word.SyncCategoryWordToServerUseCase
import am.mojtaba.armengo.core.domain.usecase.word.UpdateCategoryWordsUseCase
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
class CategoryWordV @Inject constructor(
    private val getCategoryWordsUseCase: GetCategoryWordsUseCase,
    private val updateCategoryWordsUseCase: UpdateCategoryWordsUseCase,
    private val syncCategoryWordToServerUseCase: SyncCategoryWordToServerUseCase,
    private val sortCategoryWordUseCase: SortCategoryWordUseCase,
    private val observeUnSyncedCategoryWordUseCase: ObserveUnSyncedCategoryWordUseCase
) : BaseViewModel() {

    private val _categoryWordsUiState = MutableStateFlow(UiState<List<Word>>())
    val categoryWordsUiState: StateFlow<UiState<List<Word>>> = _categoryWordsUiState.asStateFlow()

    private val _selectedCategoryId = MutableStateFlow<String?>(null)
    val selectedCategoryId: StateFlow<String?> = _selectedCategoryId.asStateFlow()

    private val _unsyncedCategoryWordState = MutableStateFlow(false)
    val unsyncedCategoryWordState: StateFlow<Boolean> = _unsyncedCategoryWordState.asStateFlow()

    init {
        observeSyncStatus()
    }

    fun observeCategoryWords(categoryId: String) {
        _selectedCategoryId.value = categoryId
        viewModelScope.launch {
            getCategoryWordsUseCase(categoryId)
                .onStart { _categoryWordsUiState.value = UiState(isLoading = true) }
                .catch { e ->
                    _categoryWordsUiState.value = UiState(error = e.message ?: "Unknown error")
                }
                .collect { words ->
                    _categoryWordsUiState.value = UiState(data = words)
                    observeSyncStatus()
                }
        }
    }

    fun observeSyncStatus() {
        viewModelScope.launch {
            observeUnSyncedCategoryWordUseCase().collect { isSyncNeeded ->
                isSyncNeeded(isSyncNeeded)
                _unsyncedCategoryWordState.value = isSyncNeeded
            }
        }
    }

    fun syncWordToServer(workerTag: String = "sync_category_word") {
        launchSyncWithEvent(
            action = { syncCategoryWordToServerUseCase(workerTag) },
            workerTag = workerTag,
            successMessage = "Category Words Synced To Server"
        )
    }

    fun updateCategoryWords(categoryId: String, selectedWordIds: List<String>) {
        launchWithEvent(
            action = { updateCategoryWordsUseCase(categoryId, selectedWordIds) },
            successMessage = "Words updated for category"
        )
    }

    fun sortCategoryWords(categoryId: String, sortedWordIds: List<String>) {
        launchWithEvent(
            action = { sortCategoryWordUseCase(categoryId, sortedWordIds) },
            successMessage = "Sorted"
        )
    }
}
