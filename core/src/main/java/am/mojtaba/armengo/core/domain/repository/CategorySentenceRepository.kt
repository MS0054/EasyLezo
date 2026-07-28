package am.mojtaba.armengo.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface CategorySentenceRepository {
    fun observeUnsynced(): Flow<Boolean>
    suspend fun sortCategorySentencesLocal(categoryId: String, orderedSentenceIds: List<String>)
    suspend fun updateCategorySentences(categoryId: String, newSentenceIds: List<String>): Result<Unit>
}