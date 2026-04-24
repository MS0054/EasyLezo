package com.appricut.easylezo.core.data.remote.model

import com.appricut.easylezo.core.domain.model.Translate

data class SentenceDto(
    val id: String = "",
    val categoryId: String = "",
    val level: String = "",
    val image: String = "",
    val order: Int = 0,
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
    val translations: List<Translate> = emptyList()
)
