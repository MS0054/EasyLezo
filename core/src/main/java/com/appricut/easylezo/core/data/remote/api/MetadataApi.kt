package com.appricut.easylezo.core.data.remote.api

import com.appricut.easylezo.core.data.remote.model.AppLanguagesDto
import com.appricut.easylezo.core.data.remote.model.LastUpdateDto
import com.appricut.easylezo.core.data.remote.model.MetadataDto
import com.appricut.easylezo.core.data.remote.model.SettingsDto
import com.appricut.easylezo.core.domain.model.ResourceDto
import com.appricut.easylezo.core.domain.model.UpdateInfoDto

interface MetadataApi {
    suspend fun getMetadata(): MetadataDto
    suspend fun updateMetadataLastUpdate( lastUpdate: LastUpdateDto)
    suspend fun updateMetadataAppLanguages( appLanguages: AppLanguagesDto)
    suspend fun updateMetadataSettings( settings: SettingsDto)
    suspend fun updateMetadataUpdateInfo( updateInfo: UpdateInfoDto)
    suspend fun updateMetadataResources( resources: List<ResourceDto>)
}
