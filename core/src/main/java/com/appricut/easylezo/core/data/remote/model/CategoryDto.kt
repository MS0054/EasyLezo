package com.appricut.easylezo.core.data.remote.model


data class CategoryDto(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val order: Int = 0,
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
    var isSynced: Boolean = false,
    var isDeleted: Boolean = false
)
