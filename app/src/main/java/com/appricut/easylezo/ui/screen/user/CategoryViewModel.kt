package com.appricut.easylezo.ui.screen.user

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
    private val userUseCase: UserUseCase,
    private val getAppLanguagesUseCase: GetAppLanguagesUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val syncAppLanguagesUseCase: SyncAppLanguagesUseCase

) : ViewModel() {

    private val _appLanguagesUiState = MutableStateFlow(UiState<AppLanguages>())
    val appLanguagesUiState: StateFlow<UiState<AppLanguages>> = _appLanguagesUiState.asStateFlow()


    private val _categoryUiState = MutableStateFlow(UiState<List<Category>>())
    val categoryUiState: StateFlow<UiState<List<Category>>> = _categoryUiState.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()
    private val _sentences = MutableStateFlow<List<Sentence>>(emptyList())
    val sentences: StateFlow<List<Sentence>> = _sentences.asStateFlow()
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    private val _selectedCategoryId = MutableStateFlow<String?>(null)
    val selectedCategoryId: StateFlow<String?> = _selectedCategoryId.asStateFlow()


    init {
        getAppLanguages()
        getCategories()
    }


    fun selectCategory(categoryId: String) = viewModelScope.launch {
        _selectedCategoryId.value = categoryId
        _loading.value = true; _error.value = null
        try {
            _sentences.value = userUseCase.fetchSentencesForCategory(categoryId)
        } catch (e: Exception) {
            _error.value = e.message
        } finally {
            _loading.value = false
        }
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