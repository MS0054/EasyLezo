package am.mojtaba.armengo.core.data.remote.api

import am.mojtaba.armengo.core.data.remote.model.CategoryWordDto

interface CategoryWordApi {
    suspend fun getCategoryWords(): List<CategoryWordDto>
    suspend fun syncCategoryWords(categoryWords: List<CategoryWordDto>)
}
