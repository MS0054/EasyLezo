package am.mojtaba.armengo.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import am.mojtaba.armengo.core.data.datastore.enums.ThemeMode
import am.mojtaba.armengo.core.domain.model.Settings
import am.mojtaba.armengo.core.domain.usecase.appLanguages.GetMetadataSettingsUseCase
import am.mojtaba.armengo.core.domain.usecase.settings.GetThemeUseCase
import am.mojtaba.armengo.core.domain.usecase.settings.SaveThemeUseCase
import am.mojtaba.armengo.ui.UiState
import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getMetadataSettingsUseCase: GetMetadataSettingsUseCase,
    private val getThemeUseCase: GetThemeUseCase,
    private val saveThemeUseCase: SaveThemeUseCase
): ViewModel()  {

    private val _settingsUiState = MutableStateFlow(UiState<Settings>())
    val settingsUiState: StateFlow<UiState<Settings>> = _settingsUiState.asStateFlow()

    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    init {
        getSettings()
        getTheme()
    }

    private fun getTheme() {
        viewModelScope.launch {
            getThemeUseCase().collect {
                Log.d("TAGG", "getTheme: $it")
                _themeMode.value = it
            }
        }
    }

    fun toggleTheme(isDark: Boolean) {
        viewModelScope.launch {
            val mode = if (isDark) ThemeMode.DARK else ThemeMode.LIGHT
            saveThemeUseCase(mode)
        }
    }

    private fun getSettings() {
        viewModelScope.launch {
            getMetadataSettingsUseCase()
                .onStart {
                    _settingsUiState.value = UiState(isLoading = true)
                }
                .catch { e ->
                    _settingsUiState.value = UiState(error = e.message ?: "Unknown error")
                }
                .collect { settings ->
                    _settingsUiState.value = UiState(data = settings)
                }
        }
    }
}