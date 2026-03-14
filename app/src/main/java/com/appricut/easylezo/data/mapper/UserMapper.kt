package com.appricut.easylezo.data.mapper

import com.appricut.easylezo.data.local.entity.UserEntity
import com.appricut.easylezo.data.remote.model.UserDto
import com.appricut.easylezo.domain.model.User

fun UserDto.toEntity() =
    UserEntity(id, uid, displayName, appLanguages.toEntity())

fun UserEntity.toDomain() =
    User(id, uid, displayName, appLanguages.toDomain())
