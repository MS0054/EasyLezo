package com.appricut.easylezo.data.remote.model

import com.appricut.easylezo.domain.model.Translate

data class SentenceDto(
    val id: String = "",
    val categoryId: String = "",
    val level: String = "",
    val image: String = "",
    val order: Int = 0,
    val translations: List<Translate> = emptyList()
)
