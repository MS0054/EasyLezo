package am.mojtaba.armengo.core.data.mapper

import am.mojtaba.armengo.core.domain.model.ReportType
import am.mojtaba.armengo.core.domain.model.ReportTypeDto

fun ReportType.toDto() =
    ReportTypeDto(id, key, description)
