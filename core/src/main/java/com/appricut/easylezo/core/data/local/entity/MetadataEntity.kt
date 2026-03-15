package com.appricut.easylezo.core.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.appricut.easylezo.core.domain.model.LastUpdate
import com.appricut.easylezo.core.domain.model.Resource
import com.appricut.easylezo.core.domain.model.Settings

@Entity(tableName = "metadata")
data class MetadataEntity(
    @PrimaryKey val id: Long = 0L,

    @Embedded(prefix = "lastUpdate_")
    var lastUpdate: LastUpdate = LastUpdate(),

    @Embedded(prefix = "settings_")
    val settings: Settings = Settings(),

    @Embedded(prefix = "resource_")
    val resource: Resource = Resource()
)