package com.appricut.easylezo.core.domain.repository

import com.appricut.easylezo.core.domain.model.AppLanguages
import com.appricut.easylezo.core.domain.model.LastUpdate
import com.appricut.easylezo.core.domain.model.Metadata
import com.appricut.easylezo.core.domain.model.Resource
import com.appricut.easylezo.core.domain.model.Settings
import kotlinx.coroutines.flow.Flow

interface MetadataRepository {
    fun observeMetadata(): Flow<Metadata>
    suspend fun syncMetadata()
    suspend fun observeMetadataServer(): Metadata
    suspend fun clearAndInsert(metadata: Metadata)
    suspend fun updateMetadataLastUpdate( lastUpdate: LastUpdate)
    suspend fun updateMetadataAppLanguages( appLanguages: AppLanguages)
    suspend fun updateMetadataSettings( settings: Settings)
    suspend fun updateMetadataResourcesServer( resources: List<Resource>)
}