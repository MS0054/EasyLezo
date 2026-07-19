package am.mojtaba.armengo.core.domain.model

data class Error(
    val id: String = "",
    val code: String = "",
    val translations: List<Translate> = emptyList()
)
