package am.mojtaba.armengo.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import am.mojtaba.armengo.core.domain.model.Settings
import am.mojtaba.armengo.core.domain.usecase.appLanguages.GetMetadataSettingsUseCase
import am.mojtaba.armengo.ui.UiState
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
 private val getMetadataSettingsUseCase: GetMetadataSettingsUseCase
): ViewModel()  {

    private val _settingsUiState = MutableStateFlow(UiState<Settings>())
    val settingsUiState: StateFlow<UiState<Settings>> = _settingsUiState.asStateFlow()

    init {
        getSettings()
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