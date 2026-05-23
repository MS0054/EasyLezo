package am.mojtaba.armengo.core.data.mapper

import am.mojtaba.armengo.core.data.local.entity.CategoryEntity
import am.mojtaba.armengo.core.data.remote.model.CategoryDto
import am.mojtaba.armengo.core.domain.model.Category

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


