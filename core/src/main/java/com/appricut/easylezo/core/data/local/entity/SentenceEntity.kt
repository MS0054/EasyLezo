package com.appricut.easylezo.core.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.appricut.easylezo.core.domain.model.Translate

@Entity(tableName = "sentence")
data class SentenceEntity(
    @PrimaryKey
    val id: String = "",
    val categoryId: String = "",
    val level: String = "",
    val image: String = "",
    val order: Int = 0,
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
    val translations: List<Translate> = emptyList()
)
