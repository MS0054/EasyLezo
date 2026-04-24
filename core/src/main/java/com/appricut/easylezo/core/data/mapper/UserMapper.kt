package com.appricut.easylezo.core.data.mapper

import com.appricut.easylezo.core.data.local.entity.UserEntity
import com.appricut.easylezo.core.data.remote.model.UserDto
import com.appricut.easylezo.core.domain.model.User

fun UserDto.toEntity() =
    UserEntity(uid, displayName,email,role, photoUrl, phoneNumber, createdAt, updatedAt,appLanguages.toEntity())

fun UserDto.toDomain() =
    User(uid, displayName,email,role,photoUrl, phoneNumber, createdAt, updatedAt, appLanguages.toDomain())

fun User.toDto() =
    UserDto(uid, displayName,email,role,photoUrl, phoneNumber, createdAt, updatedAt, appLanguages.toDto())

fun UserEntity.toDomain() =
    User( uid, displayName,email,role,photoUrl, phoneNumber, createdAt, updatedAt, appLanguages.toDomain())
