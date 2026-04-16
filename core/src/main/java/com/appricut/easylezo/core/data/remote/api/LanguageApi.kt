package com.appricut.easylezo.core.data.remote.api

import com.appricut.easylezo.core.data.remote.model.CategoryDto
import com.appricut.easylezo.core.data.remote.model.LanguageDto

interface LanguageApi {
    suspend fun getLanguages(): List<LanguageDto>
    suspend fun addLanguage(language: LanguageDto)
    suspend fun updateLanguage(language: LanguageDto)
    suspend fun deleteLanguage(language: LanguageDto)
    suspend fun sortLanguages(languages: List<LanguageDto>)
}