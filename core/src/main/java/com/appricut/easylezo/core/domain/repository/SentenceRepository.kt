package com.appricut.easylezo.core.domain.repository

import com.appricut.easylezo.core.domain.model.Sentence
import kotlinx.coroutines.flow.Flow

interface SentenceRepository {

    fun observeSentences(categoryId: String): Flow<List<Sentence>>
    suspend fun syncSentences(isForce: Boolean )

    suspend fun addSentenceLocal(sentence: Sentence)
    suspend fun addSentenceServer(sentence: Sentence)

    suspend fun updateSentenceLocal(sentence: Sentence)
    suspend fun updateSentenceServer(sentence: Sentence)

    suspend fun downloadVoice(sentences: List<Sentence>)

    suspend fun deleteSentenceLocal(sentence: Sentence)
    suspend fun deleteSentenceServer(sentence: Sentence)

    suspend fun sortSentenceLocal(sentences: List<Sentence>)
    suspend fun sortSentenceServer(sentences: List<Sentence>)
}