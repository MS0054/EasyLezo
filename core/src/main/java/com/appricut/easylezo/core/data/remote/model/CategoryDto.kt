package com.appricut.easylezo.core.data.remote.model

import com.appricut.easylezo.core.domain.model.Translate


data class CategoryDto(
    val id: String = "",
    val image: String = "",
    val order: Int = 0,
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
    var isSynced: Boolean = false,
    var isDeleted: Boolean = false,
    val translations: List<Translate> = emptyList()
)
