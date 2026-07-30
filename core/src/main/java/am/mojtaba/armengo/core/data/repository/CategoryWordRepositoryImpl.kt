package am.mojtaba.armengo.core.data.repository

import am.mojtaba.armengo.core.data.local.dao.CategoryWordDao
import am.mojtaba.armengo.core.data.local.entity.CategoryWordEntity
import am.mojtaba.armengo.core.data.remote.api.CategoryWordApi
import am.mojtaba.armengo.core.domain.repository.CategoryWordRepository
import android.util.Log
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryWordRepositoryImpl @Inject constructor(
    private val categoryWordDao: CategoryWordDao,
    private val categoryWordApi: CategoryWordApi
): CategoryWordRepository {

    override fun observeUnsynced(): Flow<Boolean> = categoryWordDao.observeUnsyncedStatus()

    override suspend fun sortCategoryWordsLocal(
        categoryId: String,
        orderedWordIds: List<String>
    ) {
        categoryWordDao.updateCategoryWordsOrder(categoryId, orderedWordIds)
    }

    override suspend fun updateCategoryWords(
        categoryId: String,
        newWordIds: List<String>
    ): Result<Unit> {
        return runCatching {
            Log.d("CategoryWordRepo", "newWordIds: $newWordIds")
            val entities = newWordIds.mapIndexed { index, wordId ->
                CategoryWordEntity(
                    id = "${categoryId}_${wordId}",
                    categoryId = categoryId,
                    wordId = wordId,
                    order = index,
                    isSynced = false,
                    isDeleted = false
                )
            }
            categoryWordDao.updateWordsForCategory(categoryId, entities)
        }
    }
}
