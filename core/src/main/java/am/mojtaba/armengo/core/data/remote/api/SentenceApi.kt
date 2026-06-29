package am.mojtaba.armengo.core.data.remote.api

import am.mojtaba.armengo.core.data.remote.model.SentenceDto

interface SentenceApi {
    suspend fun getSentences(): List<SentenceDto>
    suspend fun syncSentences(sentences: List<SentenceDto>)
    suspend fun downloadVoices(sentences: List<SentenceDto>)
}