package com.appricut.easylezo.core.data.repository

import android.util.Log
import com.appricut.easylezo.core.data.local.dao.MetadataDao
import com.appricut.easylezo.core.data.local.dao.SentenceDao
import com.appricut.easylezo.core.data.mapper.toDomain
import com.appricut.easylezo.core.data.mapper.toDto
import com.appricut.easylezo.core.data.mapper.toEntity
import com.appricut.easylezo.core.data.remote.api.SentenceApi
import com.appricut.easylezo.core.domain.model.Sentence
import com.appricut.easylezo.core.domain.repository.MetadataRepository
import com.appricut.easylezo.core.domain.repository.SentenceRepository
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

    override fun observeSentences(categoryId: String): Flow<List<Sentence>> {
        return sentenceDao.observeSentences(categoryId)
            .map { list -> list?.map { it?.toDomain() ?: Sentence()  } ?: emptyList()  }
    }


    override suspend fun syncSentences(isForce: Boolean) {
        val metadata = metadataRepository.observeMetadata().first()
        if (metadata.lastUpdate.existNewSentenceData || isForce) {
            val newSentences = sentenceApi.getSentences()
            sentenceDao.clearAll()
            sentenceDao.insertAll(newSentences.map { it.toEntity() })

            metadata.lastUpdate.existNewCategoryData = false

            metadataRepository.clearAndInsert(metadata)
        }
    }

    override suspend fun addSentenceLocal(sentence: Sentence) {
        return sentenceDao.insert(sentence.toEntity())
    }

    override suspend fun addSentenceServer(sentence: Sentence) {
        sentenceApi.addSentence(sentence.toDto())
        syncSentences(true)
    }

    override suspend fun updateSentenceLocal(sentence: Sentence) {
        return sentenceDao.update(sentence.toEntity())
    }

    override suspend fun updateSentenceServer(sentence: Sentence) {
        sentenceApi.updateSentence(sentence.toDto())
        syncSentences(true)
    }

    override suspend fun downloadVoice(sentences: List<Sentence>) {
        sentenceApi.downloadVoices(sentences.map { it.toDto() })
    }

    override suspend fun deleteSentenceLocal(sentence: Sentence) {
        return sentenceDao.delete(sentence.toEntity())
    }

    override suspend fun deleteSentenceServer(sentence: Sentence) {
        sentenceApi.deleteSentence(sentence.toDto())
        syncSentences(true)
    }

    override suspend fun sortSentenceLocal(sentences: List<Sentence>) {
        return sentenceDao.insertAll(sentences.map { it.toEntity() })
    }

    override suspend fun sortSentenceServer(sentences: List<Sentence>) {
        sentenceApi.sortSentences(sentences.map { it.toDto() })
        syncSentences(true)
    }


}
