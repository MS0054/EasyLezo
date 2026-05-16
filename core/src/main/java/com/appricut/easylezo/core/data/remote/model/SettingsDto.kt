package com.appricut.easylezo.core.data.remote.model

data class SettingsDto(
    val id: Long = 0L,
    val lastVersion: String = "",
    val policyUrl: String = "",
    val aboutUrl : String = "",
    val termsUrl : String = ""
)
