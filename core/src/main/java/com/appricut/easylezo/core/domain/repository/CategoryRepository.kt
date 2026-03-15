package com.appricut.easylezo.core.domain.repository

import com.appricut.easylezo.core.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun observeCategories(): Flow<List<Category>>

    suspend fun syncCategories()
}