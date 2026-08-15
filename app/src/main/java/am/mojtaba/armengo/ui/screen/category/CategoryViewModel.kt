package am.mojtaba.armengo.ui.screen.category

import am.mojtaba.armengo.core.domain.model.AppLanguages
import am.mojtaba.armengo.core.domain.usecase.appLanguages.GetAppLanguagesUseCase
import am.mojtaba.armengo.core.domain.usecase.appLanguages.SyncAppLanguagesUseCase
import am.mojtaba.armengo.core.domain.usecase.category.GetCategoriesUseCase
import am.mojtaba.armengo.ui.UiEvent
import am.mojtaba.armengo.core.util.ErrorMessageProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    getAppLanguagesUseCase: GetAppLanguagesUseCase,
    getCategoriesUseCase: GetCategoriesUseCase,
    private val syncAppLanguagesUseCase: SyncAppLanguagesUseCase,
    private val errorMessageProvider: ErrorMessageProvider
) : ViewModel() {


    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    private val appLanguagesFlow = getAppLanguagesUseCase().catch { throwable ->
        _uiEvent.emit(UiEvent.ShowSnackbar(errorMessageProvider.getMessage(throwable)))
        emit(AppLanguages())
    }

    private val categoriesFlow = getCategoriesUseCase().catch { throwable ->
        _uiEvent.emit(UiEvent.ShowSnackbar(errorMessageProvider.getMessage(throwable)))
        emit(emptyList())
    }


    val uiState: StateFlow<CategoryUiState> =
        combine(
            appLanguagesFlow,
            categoriesFlow
        ) { appLanguages, categories ->
            CategoryUiState(isLoading = false, appLanguages = appLanguages, categories = categories)
        }
            .onStart {
                emit(CategoryUiState(isLoading = true))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = CategoryUiState(
                    isLoading = true
                )
            )


    fun updateUserAppLanguages(appLanguages: AppLanguages?, newAppLanguage: AppLanguages) {
        viewModelScope.launch {
            runCatching {
                syncAppLanguagesUseCase(appLanguages, newAppLanguage)
            }.onFailure { throwable ->
                _uiEvent.emit(UiEvent.ShowSnackbar(errorMessageProvider.getMessage(throwable)))
            }
        }
    }
}