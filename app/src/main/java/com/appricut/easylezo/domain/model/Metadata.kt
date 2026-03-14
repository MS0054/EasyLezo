package com.appricut.easylezo.domain.model

data class Metadata(
    val id: Long = 0L,
    val lastUpdate: LastUpdate = LastUpdate(),
    val settings: Settings = Settings(),
    val resource: Resource = Resource()
)