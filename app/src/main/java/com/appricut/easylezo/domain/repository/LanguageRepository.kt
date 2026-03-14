package com.appricut.easylezo.domain.repository

import com.appricut.easylezo.domain.model.Language
import kotlinx.coroutines.flow.Flow

interface LanguageRepository {

    fun observeLanguages(): Flow<List<Language>>

    suspend fun syncLanguages()
}