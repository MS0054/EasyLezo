package am.mojtaba.armengo.core.data.repository

import am.mojtaba.armengo.core.data.local.dao.CategorySentenceDao
import am.mojtaba.armengo.core.data.local.dao.SentenceDao
import am.mojtaba.armengo.core.data.local.entity.CategorySentenceEntity
import am.mojtaba.armengo.core.data.mapper.toDomain
import am.mojtaba.armengo.core.data.mapper.toDto
import am.mojtaba.armengo.core.data.mapper.toEntity
import am.mojtaba.armengo.core.data.remote.api.CategorySentenceApi
import am.mojtaba.armengo.core.data.remote.api.SentenceApi
import am.mojtaba.armengo.core.domain.model.Sentence
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import am.mojtaba.armengo.core.domain.repository.SentenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SentenceRepositoryImpl @Inject constructor(
    private val metadataRepository: MetadataRepository,
    private val sentenceDao: SentenceDao,
    private val sentenceApi: SentenceApi,
    private val categorySentenceDao: CategorySentenceDao,
    private val categorySentenceApi: CategorySentenceApi

): SentenceRepository {

    override fun observe(categoryId: String): Flow<List<Sentence>> = sentenceDao.observe(categoryId).map { list -> list?.map { it?.toDomain() ?: Sentence()  } ?: emptyList()  }
    override fun observe(): Flow<List<Sentence>> = sentenceDao.observe().map { list -> list?.map { it?.toDomain() ?: Sentence()  } ?: emptyList()  }
    override fun observeUnsynced(): Flow<Boolean> = sentenceDao.observeUnsyncedStatus()

    override suspend fun syncFromServer(isForce: Boolean): Result<Unit> {
        return try {
            val metadata = metadataRepository.observeMetadata().first()
            if (metadata.lastUpdate.existNewSentenceData || isForce) {
                val newSentences = sentenceApi.getSentences()
                val newCategorySentence = categorySentenceApi.getCategorySentences()

                sentenceDao.upsertAll(newSentences.map { it.toEntity() })
                sentenceDao.deleteOldIds(newSentences.map { it.id })

                categorySentenceDao.clearAll()
                categorySentenceDao.upsertAll(newCategorySentence.map { it.toEntity() })

                val updatedMetadata = metadata.copy(lastUpdate = metadata.lastUpdate.copy(existNewSentenceData = false))
                metadataRepository.clearAndInsert(updatedMetadata)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addSentenceLocal(sentence: Sentence) = sentenceDao.upsert(sentence.toEntity().copy(isSynced = false))
    override suspend fun updateSentenceLocal(sentence: Sentence) = sentenceDao.upsert(sentence.toEntity().copy(isSynced = false))
    override suspend fun deleteSentenceLocal(id: String) = sentenceDao.softDelete(id)

    override suspend fun downloadVoice(sentences: List<Sentence>) = sentenceApi.downloadVoices(sentences.map { it.toDto() })


}
