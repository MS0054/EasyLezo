package am.mojtaba.armengo.admin.ui.screen.word

import am.mojtaba.armengo.admin.ui.UiState
import am.mojtaba.armengo.admin.ui.screen.BaseViewModel
import am.mojtaba.armengo.core.domain.model.Word
import am.mojtaba.armengo.core.domain.usecase.word.AddWordUseCase
import am.mojtaba.armengo.core.domain.usecase.word.DeleteWordUseCase
import am.mojtaba.armengo.core.domain.usecase.word.GetWordsUseCase
import am.mojtaba.armengo.core.domain.usecase.word.ObserveUnSyncedWordUseCase
import am.mojtaba.armengo.core.domain.usecase.word.SortWordUseCase
import am.mojtaba.armengo.core.domain.usecase.word.SyncWordFromServerUseCase
import am.mojtaba.armengo.core.domain.usecase.word.SyncWordToServerUseCase
import am.mojtaba.armengo.core.domain.usecase.word.UpdateWordUseCase
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
class WordV @Inject constructor(
    private val getWordsUseCase: GetWordsUseCase,
    private val addWordUseCase: AddWordUseCase,
    private val updateWordUseCase: UpdateWordUseCase,
    private val sortWordUseCase: SortWordUseCase,
    private val deleteWordUseCase: DeleteWordUseCase,
    private val syncWordFromServerUseCase: SyncWordFromServerUseCase,
    private val syncWordToServerUseCase: SyncWordToServerUseCase,
    private val observeUnSyncedWordUseCase: ObserveUnSyncedWordUseCase,

    ) : BaseViewModel() {


    private val _wordUiState = MutableStateFlow(UiState<List<Word>>())
    val wordUiState: StateFlow<UiState<List<Word>>> = _wordUiState.asStateFlow()

    private val _selectedCategoryId = MutableStateFlow<String?>(null)
    val selectedCategoryId: StateFlow<String?> = _selectedCategoryId.asStateFlow()

    private val _unsyncedWordState = MutableStateFlow(false)
    val unsyncedWordState: StateFlow<Boolean> = _unsyncedWordState.asStateFlow()


    init {
        observeSyncStatus()
    }

     fun observeWords(categoryId: String) {
         _selectedCategoryId.value = categoryId
        viewModelScope.launch {
            getWordsUseCase(categoryId)
                .onStart { _wordUiState.value = UiState(isLoading = true) }
                .catch { e -> _wordUiState.value = UiState(error = e.message ?: "Unknown error") }
                .collect { words ->
                    _wordUiState.value = UiState(data = words)
                    observeSyncStatus()
                }
        }
    }


    fun observeSyncStatus() {
        viewModelScope.launch {
            observeUnSyncedWordUseCase().collect { isSyncNeeded ->
                isSyncNeeded(isSyncNeeded)
                _unsyncedWordState.value = isSyncNeeded
            }
        }
    }

    fun syncWordToServer(workerTag: String = "sync_word") {
        launchSyncWithEvent(
            action = { syncWordToServerUseCase(workerTag) },
            workerTag = workerTag,
            successMessage = "Word Synced To Server"
        )
    }

    fun rejectWordChanges() {
        launchWithEvent(
            action = { syncWordFromServerUseCase(true) },
            successMessage = "Rejected Changes"
        )
    }

    fun addWord(word: Word) {
        launchWithEvent(
            action = { addWordUseCase(word) },
            successMessage = "Added"
        )
    }

    fun updateWord(word: Word) {
        launchWithEvent(
            action = { updateWordUseCase(word) },
            successMessage = "Updated"
        )
    }

    fun sortWords(sorted: List<Word>)  {
        launchWithEvent(
            action = { sortWordUseCase(sorted) },
            successMessage = "Sorted"
        )
    }

    fun deleteWord(id: String) {
        launchWithEvent(
            action = { deleteWordUseCase(id) },
            successMessage = "Deleted"
        )
    }

}