package com.appricut.easylezo.core.data.remote.model

data class LanguageDto(
    val id: String = "",
    val name: String = "",
    val code: String = "",
    val flag: String = "",
    val isFromLanguage: Boolean = false,
    val isToLanguage: Boolean = false,
    val order: Int = 0
)