package am.mojtaba.armengo.core.domain.model

data class ReportMessage(
    val id: Long = 0L,
    val key: String = "",
    val reportTypes: List<String> = emptyList(),
    val text: String = "",
    val icon: String = "",
    val description: String = ""
)
