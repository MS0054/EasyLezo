package am.mojtaba.armengo.core.data.remote.api

import am.mojtaba.armengo.core.data.remote.model.CategoryDto

interface CategoryApi {
    suspend fun getCategories(): List<CategoryDto>
    suspend fun syncCategories(categories: List<CategoryDto>)
}
