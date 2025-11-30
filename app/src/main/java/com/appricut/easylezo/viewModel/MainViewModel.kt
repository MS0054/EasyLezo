package com.appricut.easylezo.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appricut.easylezo.Repo.FirestoreRepository
import com.appricut.easylezo.data.Category
import com.appricut.easylezo.data.Sentence
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = FirestoreRepository()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    private val _sentences = MutableStateFlow<List<Sentence>>(emptyList())

    val categories: StateFlow<List<Category>> = _categories
    val sentences: StateFlow<List<Sentence>> = _sentences



    fun fetchCategories() {
        viewModelScope.launch {
            _categories.value = repository.getCategories()
        }
    }

    fun fetchSentences(categoryName: String) {
        viewModelScope.launch {
            _sentences.value = repository.getSentences(categoryName)
        }
    }
}