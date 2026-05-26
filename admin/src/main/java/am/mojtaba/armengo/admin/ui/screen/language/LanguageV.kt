package am.mojtaba.armengo.admin.ui.screen.language

import androidx.lifecycle.viewModelScope
import am.mojtaba.armengo.admin.ui.UiState
import am.mojtaba.armengo.admin.ui.screen.BaseViewModel
import am.mojtaba.armengo.admin.ui.toError
import am.mojtaba.armengo.admin.ui.toLoading
import am.mojtaba.armengo.admin.ui.toSuccess
import am.mojtaba.armengo.core.domain.model.Language
import am.mojtaba.armengo.core.domain.usecase.category.AddLanguageUseCase
import am.mojtaba.armengo.core.domain.usecase.category.DeleteLanguageUseCase
import am.mojtaba.armengo.core.domain.usecase.category.ObserveUnSyncedLanguageUseCase
import am.mojtaba.armengo.core.domain.usecase.category.SortLanguageUseCase
import am.mojtaba.armengo.core.domain.usecase.category.SyncLanguageToServerUseCase
import am.mojtaba.armengo.core.domain.usecase.category.UpdateLanguageUseCase
import am.mojtaba.armengo.core.domain.usecase.language.GetLanguagesUseCase
import am.mojtaba.armengo.core.domain.usecase.language.SyncLanguageFromServerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageV @Inject constructor(
    private val getLanguagesUseCase: GetLanguagesUseCase,
    private val addLanguageUseCase: AddLanguageUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase,
    private val deleteLanguageUseCase: DeleteLanguageUseCase,
    private val sortLanguageUseCase: SortLanguageUseCase,
    private val syncLanguageFromServerUseCase: SyncLanguageFromServerUseCase,
    private val syncLanguageToServerUseCase: SyncLanguageToServerUseCase,
    private val observeUnSyncedLanguageUseCase : ObserveUnSyncedLanguageUseCase
) : BaseViewModel() {

    private val _languageUiState = MutableStateFlow(UiState<List<Language>>(isLoading = true))
    val languageUiState: StateFlow<UiState<List<Language>>> = _languageUiState.asStateFlow()

    private val _unsyncedLanguageUiState = MutableStateFlow(UiState<Boolean>())
    val unsyncedLanguageUiState: StateFlow<UiState<Boolean>> = _unsyncedLanguageUiState.asStateFlow()


    init {
        getLanguages()
    }


    fun getLanguages() {
        viewModelScope.launch {
            getLanguagesUseCase()
                .onStart { _languageUiState.update { it.toLoading() } }
                .catch { e -> _languageUiState.update { it.toError(e.message ?: "Unknown") } }
                .collect { languages ->
                    _languageUiState.update { it.toSuccess(languages) }
                    isExistUnSyncedCategory()
                }
        }
    }
    fun isExistUnSyncedCategory(){
        viewModelScope.launch {
            observeUnSyncedLanguageUseCase().collect { isSyncNeeded ->
                isSyncNeeded(isSyncNeeded)
                _unsyncedLanguageUiState.value = UiState(data = isSyncNeeded)
            }
        }
    }
    fun syncLanguageToServer(workerTag: String = "sync_language") {
        launchSyncWithEvent(
            action = { syncLanguageToServerUseCase(workerTag) },
            workerTag = workerTag,
            successMessage = "Language Synced To Server"
        )
    }

    fun rejectLanguageChanges() {
        launchWithEvent(
            action = { syncLanguageFromServerUseCase(true) },
            successMessage = "Rejected Changes"
        )
    }

    fun addLanguage(language: Language) {
        launchWithEvent(
            action = { addLanguageUseCase(language) },
            successMessage = "Added"
        )
    }

    fun updateLanguage(language: Language) {
        launchWithEvent(
            action = { updateLanguageUseCase(language) },
            successMessage = "Updated"
        )
    }

    fun sortLanguage(sorted: List<Language>) {
        launchWithEvent(
            action = { sortLanguageUseCase(sorted) },
            successMessage = "Sorted"
        )
    }

    fun deleteLanguage(id: String) {
        launchWithEvent(
            action = { deleteLanguageUseCase(id) },
            successMessage = "Deleted"
        )
    }



}