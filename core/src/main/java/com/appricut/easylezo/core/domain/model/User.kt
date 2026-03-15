package com.appricut.easylezo.core.domain.model

data class User(
    val id: Long = 0L,
    val uid: String = "",
    val displayName: String = "",
    val appLanguages: AppLanguages = AppLanguages(),

)