package am.mojtaba.armengo.core.domain.repository

import am.mojtaba.armengo.core.domain.model.Language
import kotlinx.coroutines.flow.Flow

interface LanguageRepository {

    fun observe(): Flow<List<Language>>
    fun observeUnsyncedStatus(): Flow<Boolean>
    suspend fun syncFromServer(isForce: Boolean)
    suspend fun addLanguageLocal(language: Language)
    suspend fun updateLanguageLocal(language: Language)
    suspend fun deleteLanguageLocal(id: String)
    suspend fun sortLanguageLocal(languages: List<Language>)
}