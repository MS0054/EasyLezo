package am.mojtaba.armengo.core.data.mapper

import am.mojtaba.armengo.core.data.local.entity.AppLanguagesEntity
import am.mojtaba.armengo.core.data.remote.model.AppLanguagesDto
import am.mojtaba.armengo.core.domain.model.AppLanguages

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