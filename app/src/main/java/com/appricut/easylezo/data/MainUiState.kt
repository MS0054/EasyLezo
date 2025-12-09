package com.appricut.easylezo.data

import com.appricut.easylezo.data.model.Category
import com.appricut.easylezo.data.model.Sentence

data class MainUiState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val sentences: List<Sentence> = emptyList(),
    val selectedCategory: String? = null
)