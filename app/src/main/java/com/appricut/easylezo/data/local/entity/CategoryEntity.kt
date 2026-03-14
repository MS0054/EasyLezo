package com.appricut.easylezo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey
    val id: String = "",
    val name: String = "",
    val order: Int = 0,
    val image: String = ""
)