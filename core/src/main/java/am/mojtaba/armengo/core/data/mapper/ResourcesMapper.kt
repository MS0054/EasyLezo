package am.mojtaba.armengo.core.data.mapper

import am.mojtaba.armengo.core.domain.model.Resource
import am.mojtaba.armengo.core.domain.model.ResourceDto

fun Resource.toDto() =
    ResourceDto(id, name, description, type, url)
