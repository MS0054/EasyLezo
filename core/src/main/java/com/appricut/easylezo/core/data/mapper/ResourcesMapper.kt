package com.appricut.easylezo.core.data.mapper

import com.appricut.easylezo.core.domain.model.Resource
import com.appricut.easylezo.core.domain.model.ResourceDto

fun Resource.toDto() =
    ResourceDto(id, name, description, type, url)
