package am.mojtaba.armengo.core.data.remote.api

import am.mojtaba.armengo.core.data.remote.model.CategorySentenceDto

interface CategorySentenceApi {
    suspend fun getCategorySentences(): List<CategorySentenceDto>
    suspend fun syncCategorySentences(categorySentences: List<CategorySentenceDto>)
}
