package am.mojtaba.armengo.core.domain.model

data class ReportDto(
    val id: String = "",
    val reportMessageKey: String = "",
    val itemId: String = "",
    val userComment: String = "",
    val appVersion: String = "",
    val deviceInfo: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val userAppLanguage: String = ""
)
