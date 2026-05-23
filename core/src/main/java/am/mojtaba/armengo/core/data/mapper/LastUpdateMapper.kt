package am.mojtaba.armengo.core.data.mapper

import am.mojtaba.armengo.core.data.remote.model.LastUpdateDto
import am.mojtaba.armengo.core.domain.model.LastUpdate

fun LastUpdate.toDto() =
    LastUpdateDto( language, category, sentence)
