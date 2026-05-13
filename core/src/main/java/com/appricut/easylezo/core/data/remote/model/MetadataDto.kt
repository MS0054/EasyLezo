package com.appricut.easylezo.core.data.remote.model

import com.appricut.easylezo.core.domain.model.LastUpdate
import com.appricut.easylezo.core.domain.model.Resource
import com.appricut.easylezo.core.domain.model.Settings
import com.appricut.easylezo.core.domain.model.UpdateInfo

data class MetadataDto(
    val id: Long = 0L,
    val lastUpdate: LastUpdate = LastUpdate(),
    val updateInfo: UpdateInfo = UpdateInfo(),
    val settings: Settings = Settings(),
    val resources: List<Resource> = emptyList(),
    val appLanguages: AppLanguagesDto = AppLanguagesDto()
)