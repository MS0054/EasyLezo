package com.appricut.easylezo.core.domain.model

data class ResourceDto(
    val id: Long = 0L,
    val name: String = "",
    val description: String = "",
    val type: String = "",
    val url: String = ""
)