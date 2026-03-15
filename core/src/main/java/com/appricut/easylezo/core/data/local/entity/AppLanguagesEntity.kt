package com.appricut.easylezo.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appLanguages")
data class AppLanguagesEntity(
    @PrimaryKey
    val id : Long = 0L,
    val from: String = "",
    val to: String = "",
    val app: String = ""
)
