package am.mojtaba.armengo.core.domain.repository

import am.mojtaba.armengo.core.domain.model.AppLanguages
import am.mojtaba.armengo.core.domain.model.LastUpdate
import am.mojtaba.armengo.core.domain.model.Resource
import am.mojtaba.armengo.core.domain.model.Settings
import am.mojtaba.armengo.core.domain.model.UpdateInfo
import am.mojtaba.armengo.core.domain.model.Metadata
import kotlinx.coroutines.flow.Flow

interface MetadataRepository {
    fun observeMetadata(): Flow<Metadata>
    suspend fun syncMetadata(isForce: Boolean = false)
    suspend fun observeMetadataServer(): Metadata
    suspend fun clearAndInsert(metadata: Metadata)
    suspend fun updateMetadataLastUpdate( lastUpdate: LastUpdate)
    suspend fun updateMetadataAppLanguages( appLanguages: AppLanguages)
    suspend fun updateMetadataSettings( settings: Settings)
    suspend fun updateMetadataUpdateInfo( updateInfo: UpdateInfo)
    suspend fun updateMetadataResourcesServer( resources: List<Resource>)
}