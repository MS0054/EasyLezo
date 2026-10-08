package am.mojtaba.armengo.core.data.mapper

import am.mojtaba.armengo.core.domain.model.ReportStatusType
import am.mojtaba.armengo.core.domain.model.ReportStatusTypeDto

fun ReportStatusType.toDto() =
    ReportStatusTypeDto(id, key, color, description)
