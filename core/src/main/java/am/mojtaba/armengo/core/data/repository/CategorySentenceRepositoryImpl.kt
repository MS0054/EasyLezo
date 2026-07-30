package am.mojtaba.armengo.core.data.repository

import am.mojtaba.armengo.core.data.local.dao.CategorySentenceDao
import am.mojtaba.armengo.core.data.local.dao.SentenceDao
import am.mojtaba.armengo.core.data.local.entity.CategorySentenceEntity
import am.mojtaba.armengo.core.data.mapper.toDomain
import am.mojtaba.armengo.core.data.mapper.toDto
import am.mojtaba.armengo.core.data.mapper.toEntity
import am.mojtaba.armengo.core.data.remote.api.CategorySentenceApi
import am.mojtaba.armengo.core.data.remote.api.SentenceApi
import am.mojtaba.armengo.core.domain.model.CategorySentence
import am.mojtaba.armengo.core.domain.model.Sentence
import am.mojtaba.armengo.core.domain.repository.CategorySentenceRepository
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import am.mojtaba.armengo.core.domain.repository.SentenceRepository
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategorySentenceRepositoryImpl @Inject constructor(
    private val categorySentenceDao: CategorySentenceDao

): CategorySentenceRepository {

    override fun observeUnsynced(): Flow<Boolean> = categorySentenceDao.observeUnsyncedStatus()
    override suspend fun sortCategorySentencesLocal(
        categoryId: String,
        orderedSentenceIds: List<String>
    ) {
        categorySentenceDao.updateCategorySentencesOrder(categoryId, orderedSentenceIds)
    }
    override suspend fun updateCategorySentences(
        categoryId: String,
        newSentenceIds: List<String>
    ): Result<Unit> {
        return runCatching {
            Log.d("CategorySentenceRepo", "newSentenceIds: $newSentenceIds")
            val entities = newSentenceIds.mapIndexed { index, sentenceId ->
                CategorySentenceEntity(
                    id = "${categoryId}_${sentenceId}",
                    categoryId = categoryId,
                    sentenceId = sentenceId,
                    order = index, // 👈 حفظ دقیق ترتیب لیست ورودی
                    isSynced = false,
                    isDeleted = false
                )
            }

            categorySentenceDao.updateSentencesForCategory(categoryId, entities)
        }
    }

}
