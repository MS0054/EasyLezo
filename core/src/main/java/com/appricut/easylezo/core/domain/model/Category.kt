package com.appricut.easylezo.core.domain.model

data class Category(
    val id: String = "",
    val image: String = "",
    val order: Int = 0,
    val fromText:String = "",
    val toText: String= "",
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
    val isSynced: Boolean = false,
    val isDeleted: Boolean = false,
    val translations: List<Translate> = emptyList()
)
