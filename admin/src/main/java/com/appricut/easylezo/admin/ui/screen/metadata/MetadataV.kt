package com.appricut.easylezo.admin.ui.screen.metadata

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appricut.easylezo.admin.ui.UiState
import com.appricut.easylezo.admin.ui.screen.BaseViewModel
import com.appricut.easylezo.core.domain.model.AppLanguages
import com.appricut.easylezo.core.domain.model.Category
import com.appricut.easylezo.core.domain.model.LastUpdate
import com.appricut.easylezo.core.domain.model.Settings
import com.appricut.easylezo.core.domain.usecase.appLanguages.GetMetadataAppLanguagesUseCase
import com.appricut.easylezo.core.domain.usecase.appLanguages.GetMetadataSettingsUseCase
import com.appricut.easylezo.core.domain.usecase.lastUpdate.UpdateMetadataAppLanguagesUseCase
import com.appricut.easylezo.core.domain.usecase.lastUpdate.UpdateMetadataLastUpdateUseCase
import com.appricut.easylezo.core.domain.usecase.lastUpdate.UpdateMetadataSettingsUseCase
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
    private val updateMetadataAppLanguagesUseCase: UpdateMetadataAppLanguagesUseCase,
    private val updateMetadataSettingsUseCase: UpdateMetadataSettingsUseCase
) : BaseViewModel() {

    private val _metadataAppLanguagesUiState = MutableStateFlow(UiState<AppLanguages>())
    val metadataAppLanguagesUiState: StateFlow<UiState<AppLanguages>> = _metadataAppLanguagesUiState.asStateFlow()


    private val _metadataSettingsUiState = MutableStateFlow(UiState<Settings>())
    val metadataSettingsUiState: StateFlow<UiState<Settings>> = _metadataSettingsUiState.asStateFlow()



    init {
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

    fun updateLastUpdate(lastUpdate: LastUpdate) {
        launchWithEvent(
            action = { updateMetadataLastUpdateUseCase(lastUpdate) },
            successMessage = "LastUpdate Updated"
        )
    }

}