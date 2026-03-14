package com.appricut.easylezo.data.mapper

import com.appricut.easylezo.data.local.entity.CategoryEntity
import com.appricut.easylezo.data.remote.model.CategoryDto
import com.appricut.easylezo.domain.model.Category

fun CategoryDto.toEntity() =
    CategoryEntity(id, name, order, image)

fun CategoryEntity.toDomain() =
    Category(id, name, order, image)


