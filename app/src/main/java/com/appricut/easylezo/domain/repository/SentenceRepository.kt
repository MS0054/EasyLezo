package com.appricut.easylezo.domain.repository

import com.appricut.easylezo.domain.model.Sentence
import kotlinx.coroutines.flow.Flow

interface SentenceRepository {

    fun observeSentences(categoryId: String): Flow<List<Sentence>>

    suspend fun syncSentences()
}