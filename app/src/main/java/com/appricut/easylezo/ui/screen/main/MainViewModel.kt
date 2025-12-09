package com.appricut.easylezo.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appricut.easylezo.data.model.Category
import com.appricut.easylezo.data.model.Sentence
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: FirestoreRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _sentences = MutableStateFlow<List<Sentence>>(emptyList())
    val sentences: StateFlow<List<Sentence>> = _sentences.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // store currently selected category id for sentences fetch
    private val _selectedCategoryId = MutableStateFlow<String?>(null)
    val selectedCategoryId: StateFlow<String?> = _selectedCategoryId.asStateFlow()

    init {
        refreshCategories()
    }

    fun refreshCategories() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val cats = repo.fetchAllCategories()
                _categories.value = cats
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun selectCategory(categoryId: String) {
        _selectedCategoryId.value = categoryId
        fetchSentences(categoryId)
    }

    private fun fetchSentences(categoryId: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val sents = repo.fetchSentencesForCategory(categoryId)
                _sentences.value = sents
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    // Admin operations
    fun addSentence(categoryId: String, s: Sentence, onComplete: (Boolean, String?) -> Unit = { _, _->}) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val id = repo.addSentenceToCategory(categoryId, s)
                fetchSentences(categoryId)
                onComplete(true, id)
            } catch (e: Exception) {
                onComplete(false, e.message)
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteSentence(categoryId: String, sentenceId: String, onComplete: (Boolean, String?) -> Unit = {_,_->}) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repo.deleteSentence(categoryId, sentenceId)
                fetchSentences(categoryId)
                onComplete(true, null)
            } catch (e: Exception) {
                onComplete(false, e.message)
            } finally {
                _loading.value = false
            }
        }
    }

    // Category admin ops
    fun addCategory(cat: Category, onComplete: (Boolean, String?) -> Unit = { _, _->}) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val id = repo.addCategory(cat)
                refreshCategories()
                onComplete(true, id)
            } catch (e: Exception) {
                onComplete(false, e.message)
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteCategory(categoryId: String, onComplete: (Boolean, String?) -> Unit = {_,_->}) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repo.deleteCategory(categoryId)
                refreshCategories()
                onComplete(true, null)
            } catch (e: Exception) {
                onComplete(false, e.message)
            } finally {
                _loading.value = false
            }
        }
    }
}