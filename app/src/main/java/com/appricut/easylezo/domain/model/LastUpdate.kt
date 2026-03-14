package com.appricut.easylezo.domain.model

data class LastUpdate(
    val id: Long = 0L,
    var language: Long = 0,
    var category: Long = 0,
    var sentence: Long = 0,
    var user: Long = 0,
    var existNewUserData: Boolean = false,
    var existNewLanguageData: Boolean = false,
    var existNewCategoryData: Boolean = false,
    var existNewSentenceData: Boolean = false
)