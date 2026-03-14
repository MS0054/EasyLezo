package com.appricut.easylezo.data.remote.model

import com.appricut.easylezo.domain.model.LastUpdate
import com.appricut.easylezo.domain.model.Resource
import com.appricut.easylezo.domain.model.Settings

data class MetadataDto(
    val id: Long = 0L,
    val lastUpdate: LastUpdate = LastUpdate(),
    val settings: Settings = Settings(),
    val resource: Resource = Resource()
)