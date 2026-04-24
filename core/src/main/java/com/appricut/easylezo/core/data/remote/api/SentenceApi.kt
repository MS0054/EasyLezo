package com.appricut.easylezo.core.data.remote.api

import com.appricut.easylezo.core.data.remote.model.CategoryDto
import com.appricut.easylezo.core.data.remote.model.SentenceDto

interface SentenceApi {
    suspend fun getSentences(): List<SentenceDto>
    suspend fun addSentence(sentence: SentenceDto)
    suspend fun updateSentence(sentence: SentenceDto)
    suspend fun deleteSentence(sentence: SentenceDto)
    suspend fun sortSentences(sentences: List<SentenceDto>)
    suspend fun downloadVoices(sentences: List<SentenceDto>)
}