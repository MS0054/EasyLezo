package com.appricut.easylezo.core.domain.repository

import com.appricut.easylezo.core.domain.model.Sentence
import kotlinx.coroutines.flow.Flow

interface SentenceRepository {

    fun observeSentences(categoryId: String): Flow<List<Sentence>>

    suspend fun syncSentences()
}