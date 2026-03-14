package com.appricut.easylezo.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val id: Long = 0L,
    val uid: String = "",
    val displayName: String = "",
    @Embedded(prefix = "appLanguagesEntity_") // <--- Make sure this is here
    val appLanguages: AppLanguagesEntity = AppLanguagesEntity()
)