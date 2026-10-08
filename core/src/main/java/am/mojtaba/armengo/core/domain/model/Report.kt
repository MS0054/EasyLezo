package am.mojtaba.armengo.core.domain.model

import java.util.UUID

data class Report(
    val id: String = UUID.randomUUID().toString(),
    val reportType: String = "",
    val reportMessageKey: String = "",
    val reportStatusType: ReportStatusType = ReportStatusType(),
    val itemId: String = "",
    val userComment: String = "",
    val appVersion: String = "",
    val deviceInfo: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val userAppLanguage: String = ""
)
