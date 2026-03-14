package com.appricut.easylezo.data.remote.api

import com.appricut.easylezo.data.remote.model.LanguageDto

interface LanguageApi {
    suspend fun getLanguages(): List<LanguageDto>
}