package am.mojtaba.armengo.core.data.remote.api

import am.mojtaba.armengo.core.data.remote.model.LanguageDto

interface LanguageApi {
    suspend fun getLanguages(): List<LanguageDto>
    suspend fun syncLanguages(languages: List<LanguageDto>)
}