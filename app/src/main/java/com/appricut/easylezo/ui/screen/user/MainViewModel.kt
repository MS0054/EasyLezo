package com.appricut.easylezo.ui.screen.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appricut.easylezo.data.model.Category
import com.appricut.easylezo.data.model.Sentence
import com.appricut.easylezo.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

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

    init { refreshCategories() }

    fun refreshCategories() = viewModelScope.launch {
        _loading.value = true; _error.value = null
        try { _categories.value = userUseCase.fetchAllCategories() }
        catch (e: Exception) { _error.value = e.message }
        finally { _loading.value = false }
    }

    fun selectCategory(categoryId: String) = viewModelScope.launch {
        _selectedCategoryId.value = categoryId
        _loading.value = true; _error.value = null
        try { _sentences.value = userUseCase.fetchSentencesForCategory(categoryId) }
        catch (e: Exception) { _error.value = e.message }
        finally { _loading.value = false }
    }
}
