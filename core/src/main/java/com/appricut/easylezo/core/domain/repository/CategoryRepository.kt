package com.appricut.easylezo.core.domain.repository

import com.appricut.easylezo.core.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun observe(): Flow<List<Category>>
    suspend fun syncLocal(isForce: Boolean)

    suspend fun addCategoryLocal(category: Category)
    suspend fun addCategoryServer(category: Category)

    suspend fun updateCategoryLocal(category: Category)
    suspend fun updateCategoryServer(category: Category)


    suspend fun deleteCategoryLocal(category: Category)
    suspend fun deleteCategoryServer(category: Category)

    suspend fun sortCategoryLocal(categories: List<Category>)
    suspend fun sortCategoryServer(categories: List<Category>)
}