package com.appricut.easylezo.core.domain.model

data class Category(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val order: Int = 0,
    val createdAt: Long = 0,
    val updatedAt: Long = 0
)
