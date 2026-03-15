package com.appricut.easylezo.core.data.mapper

import com.appricut.easylezo.core.data.local.entity.UserEntity
import com.appricut.easylezo.core.data.remote.model.UserDto
import com.appricut.easylezo.core.domain.model.User

fun UserDto.toEntity() =
    UserEntity(id, uid, displayName, appLanguages.toEntity())

fun UserEntity.toDomain() =
    User(id, uid, displayName, appLanguages.toDomain())
