package com.appricut.easylezo.core.data.remote.api

import com.appricut.easylezo.core.data.remote.model.MetadataDto

interface MetadataApi {
    suspend fun getMetadata(): MetadataDto
}