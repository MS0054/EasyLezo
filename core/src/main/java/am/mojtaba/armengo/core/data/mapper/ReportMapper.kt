package am.mojtaba.armengo.core.data.mapper

import am.mojtaba.armengo.core.domain.model.Report
import am.mojtaba.armengo.core.domain.model.ReportDto

fun Report.toDto() =
    ReportDto(id, reportMessageKey, itemId, userComment, appVersion, deviceInfo, createdAt, userAppLanguage)

fun ReportDto.toDomain() =
    Report(id, reportMessageKey, itemId, userComment, appVersion, deviceInfo, createdAt, userAppLanguage)
