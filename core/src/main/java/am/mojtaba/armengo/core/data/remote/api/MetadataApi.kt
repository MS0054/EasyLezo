package am.mojtaba.armengo.core.data.remote.api

import am.mojtaba.armengo.core.data.remote.model.AppLanguagesDto
import am.mojtaba.armengo.core.data.remote.model.LastUpdateDto
import am.mojtaba.armengo.core.data.remote.model.MetadataDto
import am.mojtaba.armengo.core.data.remote.model.SettingsDto
import am.mojtaba.armengo.core.domain.model.ResourceDto
import am.mojtaba.armengo.core.domain.model.UpdateInfoDto

interface MetadataApi {
    suspend fun getMetadata(): MetadataDto
    suspend fun updateMetadataLastUpdate( lastUpdate: LastUpdateDto)
    suspend fun updateMetadataAppLanguages( appLanguages: AppLanguagesDto)
    suspend fun updateMetadataSettings( settings: SettingsDto)
    suspend fun updateMetadataUpdateInfo( updateInfo: UpdateInfoDto)
    suspend fun updateMetadataResources( resources: List<ResourceDto>)
}
