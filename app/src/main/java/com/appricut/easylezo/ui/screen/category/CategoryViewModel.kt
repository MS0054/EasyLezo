package com.appricut.easylezo.ui.screen.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appricut.easylezo.core.domain.model.AppLanguages
import com.appricut.easylezo.core.domain.model.Category
import com.appricut.easylezo.core.domain.model.Sentence
import com.appricut.easylezo.core.domain.usecase.category.GetCategoriesUseCase
import com.appricut.easylezo.core.domain.usecase.user.UserUseCase
import com.appricut.easylezo.core.domain.usecase.appLanguages.SyncAppLanguagesUseCase
import com.appricut.easylezo.core.domain.usecase.appLanguages.GetAppLanguagesUseCase
import com.appricut.easylezo.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getAppLanguagesUseCase: GetAppLanguagesUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val syncAppLanguagesUseCase: SyncAppLanguagesUseCase

) : ViewModel() {

    private val _appLanguagesUiState = MutableStateFlow(UiState<AppLanguages>())
    val appLanguagesUiState: StateFlow<UiState<AppLanguages>> = _appLanguagesUiState.asStateFlow()


    private val _categoryUiState = MutableStateFlow(UiState<List<Category>>())
    val categoryUiState: StateFlow<UiState<List<Category>>> = _categoryUiState.asStateFlow()

    init {
        getAppLanguages()
        getCategories()
    }


    private fun getCategories() {
        viewModelScope.launch {
            getCategoriesUseCase()
                .onStart {
                    _categoryUiState.value = UiState(isLoading = true)
                }
                .catch { e ->
                    _categoryUiState.value = UiState(error = e.message ?: "Unknown error")
                }
                .collect { categories ->
                    _categoryUiState.value = UiState(data = categories)
                }
        }
    }


    private fun getAppLanguages() {
        viewModelScope.launch {
            getAppLanguagesUseCase()
                .onStart {
                    _appLanguagesUiState.value = UiState(isLoading = true)
                }
                .catch { e ->
                    _appLanguagesUiState.value = UiState(error = e.message ?: "Unknown error")
                }
                .collect { appLanguages ->
                    _appLanguagesUiState.value = UiState(data = appLanguages)
                }
        }
    }

    fun updateUserAppLanguages(appLanguages: AppLanguages?, newAppLanguage: AppLanguages) {
        viewModelScope.launch {
            syncAppLanguagesUseCase(appLanguages, newAppLanguage)
        }
    }


}