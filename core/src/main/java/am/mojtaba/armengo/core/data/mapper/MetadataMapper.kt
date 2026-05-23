package am.mojtaba.armengo.core.data.mapper

import am.mojtaba.armengo.core.data.local.entity.MetadataEntity
import am.mojtaba.armengo.core.data.remote.model.MetadataDto
import am.mojtaba.armengo.core.domain.model.Metadata

fun MetadataDto.toEntity() =
    MetadataEntity(id, lastUpdate, updateInfo, settings, resources,appLanguages.toEntity())

fun Metadata.toEntity() =
    MetadataEntity(id, lastUpdate,updateInfo,settings,resources,appLanguages.toEntity())

fun MetadataDto.toDomain() =
    Metadata(id, lastUpdate, updateInfo, settings, resources, appLanguages.toDomain())

fun MetadataEntity.toDomain() =
    Metadata(id, lastUpdate, updateInfo, settings, resources,appLanguages.toDomain())
