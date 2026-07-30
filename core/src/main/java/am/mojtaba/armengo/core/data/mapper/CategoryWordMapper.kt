package am.mojtaba.armengo.core.data.mapper

import am.mojtaba.armengo.core.data.local.entity.CategoryWordEntity
import am.mojtaba.armengo.core.data.remote.model.CategoryWordDto
import am.mojtaba.armengo.core.domain.model.CategoryWord

fun CategoryWordEntity.toDto() = CategoryWordDto(id, categoryId, wordId, order, isSynced, isDeleted)
fun CategoryWord.toEntity() = CategoryWordEntity(id, categoryId, wordId, order, isSynced, isDeleted)
fun CategoryWordDto.toEntity() = CategoryWordEntity(id, categoryId, wordId, order, isSynced, isDeleted)
