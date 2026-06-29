package am.mojtaba.armengo.core.data.mapper

import am.mojtaba.armengo.core.data.local.entity.WordEntity
import am.mojtaba.armengo.core.data.remote.model.WordDto
import am.mojtaba.armengo.core.domain.model.Word

fun WordDto.toEntity() =
    WordEntity(id, categoryId, level, image, order, createdAt, updatedAt,voiceUrl, hasVoice, isSynced, isDeleted, translations)

fun Word.toEntity() =
    WordEntity(id, categoryId, level, image, order, createdAt, updatedAt,voiceUrl, hasVoice, isSynced, isDeleted, translations)

fun Word.toDto() =
    WordDto(id, categoryId, level, image, order, createdAt, updatedAt, voiceUrl, hasVoice, isSynced, isDeleted, translations)

fun WordEntity.toDto() =
    WordDto(id, categoryId, level, image, order, createdAt, updatedAt, voiceUrl, hasVoice, isSynced, isDeleted, translations)

fun WordEntity.toDomain() =
    Word(id, categoryId, level, image, "","",order, createdAt, updatedAt, voiceUrl, hasVoice, isSynced, isDeleted, translations)



