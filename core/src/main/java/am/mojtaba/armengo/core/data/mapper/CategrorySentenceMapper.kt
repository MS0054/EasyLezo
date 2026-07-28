package am.mojtaba.armengo.core.data.mapper

import am.mojtaba.armengo.core.data.local.entity.CategorySentenceEntity
import am.mojtaba.armengo.core.data.remote.model.CategorySentenceDto
import am.mojtaba.armengo.core.domain.model.CategorySentence

fun CategorySentenceEntity.toDto() = CategorySentenceDto(id, categoryId, sentenceId, order, isSynced, isDeleted)
fun CategorySentence.toEntity() = CategorySentenceEntity(id, categoryId, sentenceId, order, isSynced, isDeleted)
fun CategorySentenceDto.toEntity() = CategorySentenceEntity(id, categoryId, sentenceId, order, isSynced, isDeleted)
