package am.mojtaba.armengo.core.data.mapper

import am.mojtaba.armengo.core.domain.model.ReportMessage
import am.mojtaba.armengo.core.domain.model.ReportMessageDto

fun ReportMessage.toDto() =
    ReportMessageDto(id, key, reportTypes, text, icon, description)
