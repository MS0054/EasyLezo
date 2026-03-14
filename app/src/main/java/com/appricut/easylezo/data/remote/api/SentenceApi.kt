package com.appricut.easylezo.data.remote.api

import com.appricut.easylezo.data.remote.model.CategoryDto
import com.appricut.easylezo.data.remote.model.SentenceDto

interface SentenceApi {
    suspend fun getSentences(): List<SentenceDto>
}