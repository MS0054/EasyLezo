package com.appricut.easylezo.core.domain.repository

import com.appricut.easylezo.core.domain.model.Category
import com.appricut.easylezo.core.domain.model.Language
import kotlinx.coroutines.flow.Flow

interface LanguageRepository {

    fun observeLanguages(): Flow<List<Language>>
    suspend fun syncLanguages( isForce: Boolean )

    suspend fun addLanguageLocal(language: Language)
    suspend fun addLanguageServer(language: Language)
    suspend fun updateLanguageLocal(language: Language)
    suspend fun updateLanguageServer(language: Language)

    suspend fun deleteLanguageLocal(language: Language)
    suspend fun deleteLanguageServer(language: Language)

    suspend fun sortLanguageLocal(languages: List<Language>)
    suspend fun sortLanguageServer(languages: List<Language>)
}