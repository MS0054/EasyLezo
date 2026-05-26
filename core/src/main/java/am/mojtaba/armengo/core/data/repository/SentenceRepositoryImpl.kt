package am.mojtaba.armengo.core.data.repository

import am.mojtaba.armengo.core.data.local.dao.SentenceDao
import am.mojtaba.armengo.core.data.mapper.toDomain
import am.mojtaba.armengo.core.data.mapper.toDto
import am.mojtaba.armengo.core.data.mapper.toEntity
import am.mojtaba.armengo.core.data.remote.api.SentenceApi
import am.mojtaba.armengo.core.domain.model.Sentence
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import am.mojtaba.armengo.core.domain.repository.SentenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.map

@Singleton
class SentenceRepositoryImpl @Inject constructor(
    private val metadataRepository: MetadataRepository,
    private val sentenceDao: SentenceDao,
    private val sentenceApi: SentenceApi

): SentenceRepository {

    override fun observe(categoryId: String): Flow<List<Sentence>> {
        return sentenceDao.observe(categoryId)
            .map { list -> list?.map { it?.toDomain() ?: Sentence()  } ?: emptyList()  }
    }

    override fun observeUnsynced(): Flow<Boolean> {
        return sentenceDao.observeUnsyncedStatus()
    }

    override suspend fun syncFromServer(isForce: Boolean) {
        val metadata = metadataRepository.observeMetadata().first()
        if (metadata.lastUpdate.existNewSentenceData || isForce) {
            val newSentences = sentenceApi.getSentences()

            sentenceDao.upsertAll(newSentences.map { it.toEntity() })
            sentenceDao.deleteOldIds(newSentences.map { it.id })

            metadata.lastUpdate.existNewCategoryData = false
            metadataRepository.clearAndInsert(metadata)
        }
    }

    override suspend fun addSentenceLocal(sentence: Sentence) {
        return sentenceDao.upsert(sentence.toEntity())
    }

    override suspend fun updateSentenceLocal(sentence: Sentence) {
        return sentenceDao.upsert(sentence.toEntity())
    }

    override suspend fun deleteSentenceLocal(id: String) {
        return sentenceDao.softDelete(id)
    }

    override suspend fun sortSentenceLocal(sentences: List<Sentence>) {
        return sentenceDao.upsertAll(sentences.map { it.toEntity() })
    }

    override suspend fun downloadVoice(sentences: List<Sentence>) {
        sentenceApi.downloadVoices(sentences.map { it.toDto() })
    }

}
