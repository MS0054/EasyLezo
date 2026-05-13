package com.appricut.easylezo.core.domain.model

data class Metadata(
    val id: Long = 0L,
    val lastUpdate: LastUpdate = LastUpdate(),
    val updateInfo: UpdateInfo = UpdateInfo(),
    val settings: Settings = Settings(),
    val resources: List<Resource> = emptyList(),
    val appLanguages: AppLanguages = AppLanguages()
)
