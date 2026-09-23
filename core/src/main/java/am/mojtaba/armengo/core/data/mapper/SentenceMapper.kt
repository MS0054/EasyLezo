package am.mojtaba.armengo.core.data.mapper

import am.mojtaba.armengo.core.data.local.entity.SentenceEntity
import am.mojtaba.armengo.core.data.remote.model.SentenceDto
import am.mojtaba.armengo.core.domain.model.Sentence

fun SentenceDto.toEntity() =
    SentenceEntity(id, level, image, order, createdAt, updatedAt,voiceUrl, hasVoice, isSynced, isDeleted, translations, alternatives)

fun Sentence.toEntity() =
    SentenceEntity(id, level, image, order, createdAt, updatedAt,voiceUrl, hasVoice, isSynced, isDeleted, translations, alternatives)

fun Sentence.toDto() =
    SentenceDto(id, level, image, order, createdAt, updatedAt, voiceUrl, hasVoice, isSynced, isDeleted, translations, alternatives)

fun SentenceEntity.toDto() =
    SentenceDto(id, level, image, order, createdAt, updatedAt, voiceUrl, hasVoice, isSynced, isDeleted, translations, alternatives)

fun SentenceEntity.toDomain() =
    Sentence(id, level, image, "","",order, createdAt, updatedAt, voiceUrl, hasVoice, isSynced, isDeleted, translations, alternatives)



