package com.appricut.easylezo.data.remote.api

import com.appricut.easylezo.data.remote.model.MetadataDto

interface MetadataApi {
    suspend fun getMetadata(): MetadataDto
}