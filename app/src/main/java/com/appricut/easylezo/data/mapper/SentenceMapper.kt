package com.appricut.easylezo.data.mapper

import com.appricut.easylezo.data.local.entity.SentenceEntity
import com.appricut.easylezo.data.remote.model.SentenceDto
import com.appricut.easylezo.domain.model.Sentence

fun SentenceDto.toEntity() =
    SentenceEntity(id, categoryId, level, image, order, translations)

fun SentenceEntity.toDomain() =
    Sentence(id, categoryId, level, image, "","","","","",order, translations)



