package com.appricut.easylezo.core.data.remote.api

import com.appricut.easylezo.core.data.remote.model.CategoryDto

interface CategoryApi {
    suspend fun getCategories(): List<CategoryDto>
}