package com.appricut.easylezo.core.data.mapper

import com.appricut.easylezo.core.data.local.entity.CategoryEntity
import com.appricut.easylezo.core.data.remote.model.CategoryDto
import com.appricut.easylezo.core.domain.model.Category

fun CategoryDto.toEntity() =
    CategoryEntity(id, name, order, image)

fun CategoryEntity.toDomain() =
    Category(id, name, order, image)


