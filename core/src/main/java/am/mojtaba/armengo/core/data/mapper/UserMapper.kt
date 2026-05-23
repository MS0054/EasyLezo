package am.mojtaba.armengo.core.data.mapper

import am.mojtaba.armengo.core.data.local.entity.UserEntity
import am.mojtaba.armengo.core.data.remote.model.UserDto
import am.mojtaba.armengo.core.domain.model.User

fun UserDto.toEntity() =
    UserEntity(uid, displayName,email,role, photoUrl, phoneNumber, createdAt, updatedAt,appLanguages.toEntity())

fun UserDto.toDomain() =
    User(uid, displayName,email,role,photoUrl, phoneNumber, createdAt, updatedAt, appLanguages.toDomain())

fun User.toDto() =
    UserDto(uid, displayName,email,role,photoUrl, phoneNumber, createdAt, updatedAt, appLanguages.toDto())

fun UserEntity.toDomain() =
    User( uid, displayName,email,role,photoUrl, phoneNumber, createdAt, updatedAt, appLanguages.toDomain())
