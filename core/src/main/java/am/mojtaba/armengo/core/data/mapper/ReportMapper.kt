package am.mojtaba.armengo.core.data.mapper

import am.mojtaba.armengo.core.domain.model.Report
import am.mojtaba.armengo.core.domain.model.ReportDto

fun Report.toDto() =
    ReportDto(id, key, text, description)
