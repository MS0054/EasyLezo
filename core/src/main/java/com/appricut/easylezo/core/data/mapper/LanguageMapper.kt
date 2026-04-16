package com.appricut.easylezo.core.data.mapper

import com.appricut.easylezo.core.data.local.entity.LanguageEntity
import com.appricut.easylezo.core.data.remote.model.LanguageDto
import com.appricut.easylezo.core.domain.model.Language

fun LanguageDto.toEntity() =
    LanguageEntity(id, name, code, flag, isFromLanguage, isToLanguage, order, createdAt, updatedAt)

fun Language.toEntity() =
    LanguageEntity(id, name, code, flag, isFromLanguage, isToLanguage, order, createdAt, updatedAt)

fun Language.toDto() =
    LanguageDto(id, name, code, flag, isFromLanguage, isToLanguage, order, createdAt, updatedAt)

fun LanguageEntity.toDomain() =
    Language(id, name, code, flag, isFromLanguage, isToLanguage, order, createdAt, updatedAt)
