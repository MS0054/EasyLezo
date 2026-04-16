package com.appricut.easylezo.core.domain.manager

import com.appricut.easylezo.core.domain.model.Category

interface CategoryWM {
    suspend fun sync(categories: List<Category>)
}