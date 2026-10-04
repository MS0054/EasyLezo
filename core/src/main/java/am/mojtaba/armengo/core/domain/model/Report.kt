package am.mojtaba.armengo.core.domain.model

import java.util.UUID

data class Report(
    val id: String = UUID.randomUUID().toString(),
    val reportMessageKey: String = "",
    val itemId: String = "",
    val userComment: String = "",
    val appVersion: String = "",
    val deviceInfo: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val userAppLanguage: String = ""
)
