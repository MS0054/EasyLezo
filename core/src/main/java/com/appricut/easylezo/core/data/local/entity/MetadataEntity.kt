package com.appricut.easylezo.core.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.appricut.easylezo.core.domain.model.LastUpdate
import com.appricut.easylezo.core.domain.model.Resource
import com.appricut.easylezo.core.domain.model.Settings
import com.appricut.easylezo.core.domain.model.UpdateInfo

@Entity(tableName = "metadata")
data class MetadataEntity(
    @PrimaryKey val id: Long = 0L,

    @Embedded(prefix = "lastUpdate_")
    var lastUpdate: LastUpdate = LastUpdate(),

    @Embedded(prefix = "updateInfo_")
    val updateInfo: UpdateInfo = UpdateInfo(),

    @Embedded(prefix = "settings_")
    val settings: Settings = Settings(),

    // this field has specific TypeConverter instead of Embedded
    val resources: List<Resource> = emptyList(),

    @Embedded(prefix = "appLanguage_")
    val appLanguages: AppLanguagesEntity = AppLanguagesEntity()
)