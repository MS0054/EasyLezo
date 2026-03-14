package com.appricut.easylezo.data.repository

import android.util.Log
import com.appricut.easylezo.data.local.dao.MetadataDao
import com.appricut.easylezo.data.local.dao.SentenceDao
import com.appricut.easylezo.data.mapper.toDomain
import com.appricut.easylezo.data.mapper.toEntity
import com.appricut.easylezo.data.remote.api.SentenceApi
import com.appricut.easylezo.domain.model.Sentence
import com.appricut.easylezo.domain.repository.MetadataRepository
import com.appricut.easylezo.domain.repository.SentenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SentenceRepositoryImpl @Inject constructor(
    private val metadataRepository: MetadataRepository,
    private val sentenceDao: SentenceDao,
    private val sentenceApi: SentenceApi

): SentenceRepository {

    override fun observeSentences(categoryId: String): Flow<List<Sentence>> {
        return sentenceDao.observeSentences(categoryId)
            .map { list -> list.map { it.toDomain() } }
    }


    override suspend fun syncSentences() {
        val metadata = metadataRepository.observeMetadata().first()
        if (metadata.lastUpdate.existNewSentenceData) {
            val newSentences = sentenceApi.getSentences()
            sentenceDao.clearAll()
            sentenceDao.insertAll(newSentences.map { it.toEntity() })

            metadata.lastUpdate.existNewCategoryData = false

            metadataRepository.clearAndInsert(metadata)
        }
    }


}
