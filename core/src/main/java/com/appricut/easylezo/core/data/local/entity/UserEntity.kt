package com.appricut.easylezo.core.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val uid: String = "",
    val displayName: String = "",
    val email: String = "",
    val role: String = "",
    val photoUrl: String = "",
    val phoneNumber: String = "",
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
    @Embedded(prefix = "appLanguagesEntity_") // <--- Make sure this is here
    val appLanguages: AppLanguagesEntity = AppLanguagesEntity()
)