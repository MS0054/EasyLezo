package com.appricut.easylezo.core.data.remote.model

data class UserDto(
    val uid: String = "",
    val displayName: String = "",
    val email: String = "",
    val role: String = "",
    val photoUrl: String = "",
    val phoneNumber: String = "",
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
    val appLanguages: AppLanguagesDto = AppLanguagesDto(),
    )