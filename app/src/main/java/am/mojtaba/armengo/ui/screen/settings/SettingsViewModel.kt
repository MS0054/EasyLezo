package am.mojtaba.armengo.ui.screen.settings

import am.mojtaba.armengo.core.data.datastore.enums.ThemeMode
import am.mojtaba.armengo.core.domain.usecase.appLanguages.GetMetadataSettingsUseCase
import am.mojtaba.armengo.core.domain.usecase.settings.GetThemeUseCase
import am.mojtaba.armengo.core.domain.usecase.settings.SaveThemeUseCase
import am.mojtaba.armengo.core.domain.usecase.user.GetUserUseCase
import am.mojtaba.armengo.core.domain.usecase.user.SyncUserUseCase
import am.mojtaba.armengo.core.domain.usecase.auth.SignOutUseCase
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
class SettingsViewModel @Inject constructor(
    getMetadataSettingsUseCase: GetMetadataSettingsUseCase,
    getThemeUseCase: GetThemeUseCase,
    getUserUseCase: GetUserUseCase,
    private val syncUserUseCase: SyncUserUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val saveThemeUseCase: SaveThemeUseCase,
    private val errorMessageProvider: ErrorMessageProvider
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            runCatching {
                syncUserUseCase()
            }.onFailure { throwable ->
                _uiEvent.emit(UiEvent.ShowSnackbar(errorMessageProvider.getMessage(throwable)))
            }
        }
    }

    private val settingsFlow = getMetadataSettingsUseCase().catch { throwable ->
        _uiEvent.emit(UiEvent.ShowSnackbar(errorMessageProvider.getMessage(throwable)))
    }

    private val themeFlow = getThemeUseCase().catch { throwable ->
        _uiEvent.emit(UiEvent.ShowSnackbar(errorMessageProvider.getMessage(throwable)))
    }

    private val userFlow = getUserUseCase().catch { throwable ->
        _uiEvent.emit(UiEvent.ShowSnackbar(errorMessageProvider.getMessage(throwable)))
    }

    val uiState: StateFlow<SettingsUiState> = combine(
        settingsFlow,
        themeFlow,
        userFlow
    ) { settings, themeMode, user ->
        SettingsUiState(
            isLoading = false,
            settings = settings,
            themeMode = themeMode,
            user = user
        )
    }
        .onStart { emit(SettingsUiState(isLoading = true)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SettingsUiState(isLoading = true)
        )

    fun toggleTheme(isDark: Boolean) {
        viewModelScope.launch {
            runCatching {
                val mode = if (isDark) ThemeMode.DARK else ThemeMode.LIGHT
                saveThemeUseCase(mode)
            }.onFailure { throwable ->
                _uiEvent.emit(UiEvent.ShowSnackbar(errorMessageProvider.getMessage(throwable)))
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            runCatching {
                signOutUseCase()
            }.onFailure { throwable ->
                _uiEvent.emit(UiEvent.ShowSnackbar(errorMessageProvider.getMessage(throwable)))
            }
        }
    }
}