package com.appricut.easylezo.core.data.remote.api

import com.appricut.easylezo.core.data.remote.model.CategoryDto
import com.appricut.easylezo.core.data.remote.model.SentenceDto

interface SentenceApi {
    suspend fun getSentences(): List<SentenceDto>
}