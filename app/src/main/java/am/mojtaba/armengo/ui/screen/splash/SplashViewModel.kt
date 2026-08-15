package am.mojtaba.armengo.ui.screen.splash

import am.mojtaba.armengo.core.domain.usecase.category.SyncCategoryFromServerUseCase
import am.mojtaba.armengo.core.domain.usecase.language.SyncLanguageFromServerUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.CheckUpdateUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.SyncImageFromServerUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.SyncMetadataUseCase
import am.mojtaba.armengo.core.domain.usecase.sentence.SyncSentenceFromServerUseCase
import am.mojtaba.armengo.core.domain.usecase.word.SyncWordFromServerUseCase
import am.mojtaba.armengo.ui.UiEvent
import am.mojtaba.armengo.core.util.ErrorMessageProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val syncMetadataUseCase: SyncMetadataUseCase,
    private val syncLanguageFromServerUseCase: SyncLanguageFromServerUseCase,
    private val syncCategoryFromServerUseCase: SyncCategoryFromServerUseCase,
    private val syncWordFromServerUseCase: SyncWordFromServerUseCase,
    private val syncSentenceFromServerUseCase: SyncSentenceFromServerUseCase,
    private val syncImageFromServerUseCase: SyncImageFromServerUseCase,
    private val checkUpdateUseCase: CheckUpdateUseCase,
    private val errorMessageProvider: ErrorMessageProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    init {
        start()
    }

    fun start() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, updateStatus = UpdateStatus.Idle) }
            try {
                syncMetadataUseCase()
                checkAppUpdate()
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, updateStatus = UpdateStatus.Error) }
                _uiEvent.emit(UiEvent.ShowSnackbar(errorMessageProvider.getMessage(e)))
            }
        }
    }

    private fun checkAppUpdate() {
        viewModelScope.launch {
            try {
                val updateInfo = checkUpdateUseCase()
                joinAll(
                    async { syncCategoryFromServerUseCase() },
                    async { syncImageFromServerUseCase() },
                    async { syncLanguageFromServerUseCase() },
                    async { syncSentenceFromServerUseCase() },
                    async { syncWordFromServerUseCase() }
                )
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        updateStatus = UpdateStatus.Success(updateInfo)
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        updateStatus = UpdateStatus.Error
                    )
                }
                _uiEvent.emit(UiEvent.ShowSnackbar(errorMessageProvider.getMessage(e)))
            }
        }
    }
}