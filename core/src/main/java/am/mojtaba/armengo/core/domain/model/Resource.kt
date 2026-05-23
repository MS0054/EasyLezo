package am.mojtaba.armengo.core.domain.model

data class Resource(
    val id: Long = 0L,
    val name: String = "",
    val description: String = "",
    val type: String = "",
    val url: String = ""
)