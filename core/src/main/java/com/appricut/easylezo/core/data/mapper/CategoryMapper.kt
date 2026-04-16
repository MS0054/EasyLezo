package com.appricut.easylezo.core.data.mapper

import com.appricut.easylezo.core.data.local.entity.CategoryEntity
import com.appricut.easylezo.core.data.remote.model.CategoryDto
import com.appricut.easylezo.core.domain.model.Category

fun CategoryDto.toEntity() =
    CategoryEntity(id, name, image, order, createdAt, updatedAt)

fun Category.toEntity() =
    CategoryEntity(id, name, image, order, createdAt, updatedAt)

fun Category.toDto() =
    CategoryDto(id, name, image, order, createdAt, updatedAt)

fun CategoryEntity.toDomain() =
    Category(id, name, image, order, createdAt, updatedAt)


