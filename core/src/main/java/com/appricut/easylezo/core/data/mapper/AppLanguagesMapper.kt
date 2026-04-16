package com.appricut.easylezo.core.data.mapper

import com.appricut.easylezo.core.data.local.entity.AppLanguagesEntity
import com.appricut.easylezo.core.data.local.entity.UserEntity
import com.appricut.easylezo.core.data.remote.model.AppLanguagesDto
import com.appricut.easylezo.core.data.remote.model.UserDto
import com.appricut.easylezo.core.domain.model.AppLanguages
import com.appricut.easylezo.core.domain.model.User

fun AppLanguagesDto.toEntity() =
    AppLanguagesEntity(id, from, to, app)

fun AppLanguages.toEntity() =
    AppLanguagesEntity(id, from, to, app)

fun AppLanguages.toDto() =
    AppLanguagesDto(id, from, to, app)

fun AppLanguagesEntity.toDomain() =
    AppLanguages(id, from, to, null,null, emptyList(), emptyList(),emptyList(), app)

fun AppLanguagesDto.toDomain() =
    AppLanguages(id, from, to, null,null, emptyList(), emptyList(),emptyList(), app)


fun AppLanguagesEntity.toDto() =
    AppLanguagesDto(id, from, to, app)