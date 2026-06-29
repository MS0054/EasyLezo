package am.mojtaba.armengo.core.data.mapper

import am.mojtaba.armengo.core.data.local.entity.LanguageEntity
import am.mojtaba.armengo.core.data.remote.model.LanguageDto
import am.mojtaba.armengo.core.domain.model.Language

fun LanguageDto.toEntity() =
    LanguageEntity(id, name, code, flag, isFromLanguage, isToLanguage, order, createdAt, updatedAt, isSynced, isDeleted)

fun Language.toEntity() =
    LanguageEntity(id, name, code, flag, isFromLanguage, isToLanguage, order, createdAt, updatedAt, isSynced, isDeleted)

fun Language.toDto() =
    LanguageDto(id, name, code, flag, isFromLanguage, isToLanguage, order, createdAt, updatedAt, isSynced, isDeleted)

fun LanguageEntity.toDto() =
    LanguageDto(id, name, code, flag, isFromLanguage, isToLanguage, order, createdAt, updatedAt, isSynced, isDeleted)

fun LanguageEntity.toDomain() =
    Language(id, name, code, flag, isFromLanguage, isToLanguage, order, createdAt, updatedAt, isSynced, isDeleted)
