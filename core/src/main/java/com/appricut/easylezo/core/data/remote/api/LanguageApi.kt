package com.appricut.easylezo.core.data.remote.api

import com.appricut.easylezo.core.data.remote.model.LanguageDto

interface LanguageApi {
    suspend fun getLanguages(): List<LanguageDto>
}