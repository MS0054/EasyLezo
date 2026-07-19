package am.mojtaba.armengo.core.data.mapper

import am.mojtaba.armengo.core.data.remote.model.ErrorDto
import am.mojtaba.armengo.core.domain.model.Error

fun Error.toDto() =
    ErrorDto(id, code, translations )
