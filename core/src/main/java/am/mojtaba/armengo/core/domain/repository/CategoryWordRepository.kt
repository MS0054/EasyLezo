package am.mojtaba.armengo.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface CategoryWordRepository {
    fun observeUnsynced(): Flow<Boolean>
    suspend fun sortCategoryWordsLocal(categoryId: String, orderedWordIds: List<String>)
    suspend fun updateCategoryWords(categoryId: String, newWordIds: List<String>): Result<Unit>
}
