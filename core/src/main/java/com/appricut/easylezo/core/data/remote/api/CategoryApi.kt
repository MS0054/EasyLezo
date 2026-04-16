package com.appricut.easylezo.core.data.remote.api

import com.appricut.easylezo.core.data.remote.model.CategoryDto

interface CategoryApi {
    suspend fun getCategories(): List<CategoryDto>
    suspend fun addCategory(category: CategoryDto)
    suspend fun updateCategory(category: CategoryDto)
    suspend fun deleteCategory(category: CategoryDto)

    suspend fun sortCategories(categories: List<CategoryDto>)
}
