package com.appricut.easylezo.domain.repository

import com.appricut.easylezo.domain.model.Metadata
import kotlinx.coroutines.flow.Flow

interface MetadataRepository {
    fun observeMetadata(): Flow<Metadata>
    suspend fun syncMetadata()
    suspend fun clearAndInsert(metadata: Metadata)
}