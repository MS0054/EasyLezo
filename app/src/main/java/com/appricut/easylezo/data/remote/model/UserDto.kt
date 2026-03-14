package com.appricut.easylezo.data.remote.model

data class UserDto(
    val id: Long = 0L,
    val uid: String = "",
    val displayName: String = "",
    val appLanguages: AppLanguagesDto = AppLanguagesDto(),

    )