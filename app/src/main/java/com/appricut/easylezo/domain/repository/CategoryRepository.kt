package com.appricut.easylezo.domain.repository

import com.appricut.easylezo.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun observeCategories(): Flow<List<Category>>

    suspend fun syncCategories()
}