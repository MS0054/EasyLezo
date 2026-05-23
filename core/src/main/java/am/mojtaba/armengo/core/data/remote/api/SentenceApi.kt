package am.mojtaba.armengo.core.data.remote.api

import am.mojtaba.armengo.core.data.remote.model.SentenceDto

interface SentenceApi {
    suspend fun getSentences(): List<SentenceDto>
    suspend fun addSentence(sentence: SentenceDto)
    suspend fun updateSentence(sentence: SentenceDto)
    suspend fun deleteSentence(sentence: SentenceDto)
    suspend fun sortSentences(sentences: List<SentenceDto>)
    suspend fun downloadVoices(sentences: List<SentenceDto>)
}