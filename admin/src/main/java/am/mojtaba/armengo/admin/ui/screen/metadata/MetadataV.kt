package am.mojtaba.armengo.admin.ui.screen.metadata


import androidx.lifecycle.viewModelScope
import am.mojtaba.armengo.admin.ui.UiState
import am.mojtaba.armengo.admin.ui.screen.BaseViewModel
import am.mojtaba.armengo.core.domain.model.AppLanguages
import am.mojtaba.armengo.core.domain.model.LastUpdate
import am.mojtaba.armengo.core.domain.model.Settings
import am.mojtaba.armengo.core.domain.model.UpdateInfo
import am.mojtaba.armengo.core.domain.usecase.appLanguages.GetMetadataAppLanguagesUseCase
import am.mojtaba.armengo.core.domain.usecase.appLanguages.GetMetadataSettingsUseCase
import am.mojtaba.armengo.core.domain.usecase.appLanguages.GetMetadataUpdateInfoUseCase
import am.mojtaba.armengo.core.domain.usecase.lastUpdate.UpdateMetadataAppLanguagesUseCase
import am.mojtaba.armengo.core.domain.usecase.lastUpdate.UpdateMetadataLastUpdateUseCase
import am.mojtaba.armengo.core.domain.usecase.lastUpdate.UpdateMetadataSettingsUseCase
import am.mojtaba.armengo.core.domain.usecase.lastUpdate.UpdateMetadataUpdateInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MetadataV @Inject constructor(
    private val updateMetadataLastUpdateUseCase: UpdateMetadataLastUpdateUseCase,
    private val getMetadataAppLanguagesUseCase: GetMetadataAppLanguagesUseCase,
    private val getMetadataSettingsUseCase: GetMetadataSettingsUseCase,
    private val getMetadataUpdateInfoUseCase: GetMetadataUpdateInfoUseCase,
    private val updateMetadataAppLanguagesUseCase: UpdateMetadataAppLanguagesUseCase,
    private val updateMetadataSettingsUseCase: UpdateMetadataSettingsUseCase,
    private val updateMetadataUpdateInfoUseCase: UpdateMetadataUpdateInfoUseCase,
) : BaseViewModel() {

    private val _metadataAppLanguagesUiState = MutableStateFlow(UiState<AppLanguages>())
    val metadataAppLanguagesUiState: StateFlow<UiState<AppLanguages>> = _metadataAppLanguagesUiState.asStateFlow()


    private val _metadataSettingsUiState = MutableStateFlow(UiState<Settings>())
    val metadataSettingsUiState: StateFlow<UiState<Settings>> = _metadataSettingsUiState.asStateFlow()

    private val _metadataUpdateInfoUiState = MutableStateFlow(UiState<UpdateInfo>())
    val metadataUpdateInfoUiState: StateFlow<UiState<UpdateInfo>> = _metadataUpdateInfoUiState.asStateFlow()


    init {
        getMetadataUpdateInfo()
        getMetadataAppLanguages()
        getMetadataSettings()
    }


    fun getMetadataSettings() {
        viewModelScope.launch {
            getMetadataSettingsUseCase()
                .onStart {
                    _metadataSettingsUiState.value = UiState(isLoading = true)
                }
                .catch { e ->
                    _metadataSettingsUiState.value = UiState(error = e.message ?: "Unknown error")
                }
                .collect { settings->
                    _metadataSettingsUiState.value = UiState(data = settings)
                }
        }
    }


    fun getMetadataUpdateInfo() {
        viewModelScope.launch {
            getMetadataUpdateInfoUseCase()
                .onStart {
                    _metadataUpdateInfoUiState.value = UiState(isLoading = true)
                }
                .catch { e ->
                    _metadataUpdateInfoUiState.value = UiState(error = e.message ?: "Unknown error")
                }
                .collect { updateInfo->
                    _metadataUpdateInfoUiState.value = UiState(data = updateInfo)
                }
        }
    }


    fun getMetadataAppLanguages() {
        viewModelScope.launch {
            getMetadataAppLanguagesUseCase()
                .onStart {
                    _metadataAppLanguagesUiState.value = UiState(isLoading = true)
                }
                .catch { e ->
                    _metadataAppLanguagesUiState.value = UiState(error = e.message ?: "Unknown error")
                }
                .collect { appLanguages ->
                    _metadataAppLanguagesUiState.value = UiState(data = appLanguages)
                }
        }
    }

    fun updateMetadataAppLanguages(appLanguages: AppLanguages) {
        launchWithEvent(
            action = { updateMetadataAppLanguagesUseCase(appLanguages) },
            successMessage = "AppLanguage Updated"
        )
    }

    fun updateMetadataSettings(settings: Settings) {
        launchWithEvent(
            action = { updateMetadataSettingsUseCase(settings) },
            successMessage = "Settings Updated"
        )
    }

    fun updateMetadataUpdateInfo(updateInfo: UpdateInfo) {
        launchWithEvent(
            action = { updateMetadataUpdateInfoUseCase(updateInfo) },
            successMessage = "UpdateInfo Updated"
        )
    }

    fun updateLastUpdate(lastUpdate: LastUpdate) {
        launchWithEvent(
            action = { updateMetadataLastUpdateUseCase(lastUpdate) },
            successMessage = "LastUpdate Updated"
        )
    }

}