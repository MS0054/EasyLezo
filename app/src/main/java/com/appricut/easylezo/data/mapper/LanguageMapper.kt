package com.appricut.easylezo.data.mapper

import com.appricut.easylezo.data.local.entity.LanguageEntity
import com.appricut.easylezo.data.remote.model.LanguageDto
import com.appricut.easylezo.domain.model.Language

fun LanguageDto.toEntity() =
    LanguageEntity(id, name, code, flag, isFromLanguage, isToLanguage, order)

fun LanguageEntity.toDomain() =
    Language(id, name, code, flag, isFromLanguage, isToLanguage, order)
