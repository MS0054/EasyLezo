package com.appricut.easylezo.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val order: Int = 0,
    val createdAt: Long = 0,
    val updatedAt: Long = 0
)