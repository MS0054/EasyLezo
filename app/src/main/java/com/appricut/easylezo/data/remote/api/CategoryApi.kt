package com.appricut.easylezo.data.remote.api

import com.appricut.easylezo.data.remote.model.CategoryDto

interface CategoryApi {
    suspend fun getCategories(): List<CategoryDto>
}