package am.mojtaba.armengo.core.data.remote.api

import am.mojtaba.armengo.core.data.remote.model.WordDto

interface WordApi {
    suspend fun getWords(): List<WordDto>
    suspend fun syncWords(words: List<WordDto>)
    suspend fun downloadVoices(words: List<WordDto>)
}