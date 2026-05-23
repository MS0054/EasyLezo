package am.mojtaba.armengo.core.domain.model

data class Language(
    val id: String = "",
    val name: String = "",
    val code: String = "",
    val flag: String = "",
    val isFromLanguage: Boolean = false,
    val isToLanguage: Boolean = false,
    val order: Int = 0,
    val createdAt: Long = 0,
    val updatedAt: Long = 0
)