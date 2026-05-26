package am.mojtaba.armengo.core.data.mapper

import am.mojtaba.armengo.core.data.local.entity.SentenceEntity
import am.mojtaba.armengo.core.data.remote.model.SentenceDto
import am.mojtaba.armengo.core.domain.model.Sentence

fun SentenceDto.toEntity() =
    SentenceEntity(id, categoryId, level, image, order, createdAt, updatedAt, isSynced, isDeleted, translations)

fun Sentence.toEntity() =
    SentenceEntity(id, categoryId, level, image, order, createdAt, updatedAt, isSynced, isDeleted, translations)

fun Sentence.toDto() =
    SentenceDto(id, categoryId, level, image, order, createdAt, updatedAt, isSynced, isDeleted, translations)

fun SentenceEntity.toDto() =
    SentenceDto(id, categoryId, level, image, order, createdAt, updatedAt, isSynced, isDeleted, translations)

fun SentenceEntity.toDomain() =
    Sentence(id, categoryId, level, image, "","",order, createdAt, updatedAt, isSynced, isDeleted, translations)



