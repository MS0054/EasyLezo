package com.appricut.easylezo.core.data.mapper

import com.appricut.easylezo.core.data.local.entity.MetadataEntity
import com.appricut.easylezo.core.data.remote.model.MetadataDto
import com.appricut.easylezo.core.domain.model.Metadata

fun MetadataDto.toEntity() =
    MetadataEntity(id, lastUpdate, updateInfo, settings, resources,appLanguages.toEntity())

fun Metadata.toEntity() =
    MetadataEntity(id, lastUpdate,updateInfo,settings,resources,appLanguages.toEntity())

fun MetadataDto.toDomain() =
    Metadata(id, lastUpdate, updateInfo, settings, resources, appLanguages.toDomain())

fun MetadataEntity.toDomain() =
    Metadata(id, lastUpdate, updateInfo, settings, resources,appLanguages.toDomain())
