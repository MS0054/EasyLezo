package com.appricut.easylezo.core.data.mapper

import com.appricut.easylezo.core.data.local.entity.SentenceEntity
import com.appricut.easylezo.core.data.remote.model.SentenceDto
import com.appricut.easylezo.core.domain.model.Sentence

fun SentenceDto.toEntity() =
    SentenceEntity(id, categoryId, level, image, order, translations)

fun SentenceEntity.toDomain() =
    Sentence(id, categoryId, level, image, "","","","","",order, translations)



