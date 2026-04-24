package com.appricut.easylezo.core.data.mapper

import com.appricut.easylezo.core.data.local.entity.CategoryEntity
import com.appricut.easylezo.core.data.remote.model.CategoryDto
import com.appricut.easylezo.core.domain.model.Category

fun CategoryDto.toEntity() =
    CategoryEntity(id, image, order, createdAt, updatedAt, translations = translations)

fun Category.toEntity() =
    CategoryEntity(id, image, order, createdAt, updatedAt, isSynced, isDeleted, translations)

fun Category.toDto() =
    CategoryDto(id, image, order, createdAt, updatedAt, isSynced, isDeleted, translations)

fun CategoryEntity.toDto() =
    CategoryDto(id, image, order, createdAt, updatedAt, isSynced, isDeleted, translations)

fun CategoryEntity.toDomain() =
    Category(id, image, order, "","",createdAt, updatedAt, isSynced, isDeleted, translations)


