package com.appricut.easylezo.core.domain.repository

import com.appricut.easylezo.core.domain.model.Language
import kotlinx.coroutines.flow.Flow

interface LanguageRepository {

    fun observeLanguages(): Flow<List<Language>>

    suspend fun syncLanguages()
}