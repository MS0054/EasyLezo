package com.appricut.easylezo.ui.screen.admin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appricut.easylezo.core.domain.model.Category
import com.appricut.easylezo.core.domain.model.Sentence
import com.appricut.easylezo.core.domain.usecase.AdminUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val adminUseCase: AdminUseCase
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
        try { _categories.value = adminUseCase.fetchAllCategories() }
        catch (e: Exception) { _error.value = e.message }
        finally { _loading.value = false }
    }

    fun selectCategory(categoryId: String) = viewModelScope.launch {
        Log.i("gogogo,", "111 :$categoryId")
        _selectedCategoryId.value = categoryId
        _loading.value = true; _error.value = null
        try { _sentences.value = adminUseCase.fetchSentences(categoryId)
            Log.i("gogogo,", "111 :${_sentences.value}")}
        catch (e: Exception) { _error.value = e.message }
        finally { _loading.value = false }
    }

    fun addCategory(cat: Category, onComplete: (Boolean, String?) -> Unit = { _, _ -> }) = viewModelScope.launch {
        _loading.value = true
        try { val id = adminUseCase.addCategory(cat); refreshCategories(); onComplete(true, id) }
        catch (e: Exception) { onComplete(false, e.message) }
        finally { _loading.value = false }
    }

    fun updateCategory(cat: Category, onComplete: (Boolean, String?) -> Unit = { _, _ -> }) = viewModelScope.launch {
        _loading.value = true
        _error.value = null
        try {
            adminUseCase.updateCategory(cat)   // ← فراخوانی یوزکیس
            refreshCategories()                // ← بعد از آپدیت، لیست دوباره لود شود
            onComplete(true, null)
        } catch (e: Exception) {
            _error.value = e.message
            onComplete(false, e.message)
        } finally {
            _loading.value = false
        }
    }

    fun deleteCategory(categoryId: String, onComplete: (Boolean, String?) -> Unit = { _, _ -> }) = viewModelScope.launch {
        _loading.value = true
        try { adminUseCase.deleteCategory(categoryId); refreshCategories(); onComplete(true, null) }
        catch (e: Exception) { onComplete(false, e.message) }
        finally { _loading.value = false }
    }


    fun saveCategoryOrder(sorted: List<Category>) = viewModelScope.launch {
        try {
            _categories.value = sorted // آپدیت state
            adminUseCase.updateCategoriesOrder(sorted)
        } catch (e: Exception) {
            _error.value = e.message
        }
    }

    fun addSentence(categoryId: String, s: Sentence, onComplete: (Boolean, String?) -> Unit = { _, _ -> }) = viewModelScope.launch {
        _loading.value = true
        try { val id = adminUseCase.addSentence(categoryId, s); selectCategory(categoryId); onComplete(true, id) }
        catch (e: Exception) { onComplete(false, e.message) }
        finally { _loading.value = false }
    }

    fun deleteSentence(categoryId: String, sentenceId: String, onComplete: (Boolean, String?) -> Unit = { _, _ -> }) = viewModelScope.launch {
        _loading.value = true
        try { adminUseCase.deleteSentence(categoryId, sentenceId); selectCategory(categoryId); onComplete(true, null) }
        catch (e: Exception) { onComplete(false, e.message) }
        finally { _loading.value = false }
    }

    fun updateSentence(
        categoryId: String,
        s: Sentence,
        onComplete: (Boolean, String?) -> Unit = { _, _ -> }
    ) = viewModelScope.launch {
        _loading.value = true
        _error.value = null
        try {
            adminUseCase.updateSentence(categoryId, s)
            selectCategory(categoryId) // بعد از آپدیت، لیست جمله‌ها رفرش شود
            onComplete(true, null)
        } catch (e: Exception) {
            _error.value = e.message
            onComplete(false, e.message)
        } finally {
            _loading.value = false
        }
    }

    fun saveSentenceOrder(categoryId: String, sorted: List<Sentence>) = viewModelScope.launch {
        try {
            _sentences.value = sorted // آپدیت state
            adminUseCase.updateSentencesOrder(categoryId, sorted)
        } catch (e: Exception) {
            _error.value = e.message
        }
    }


}
