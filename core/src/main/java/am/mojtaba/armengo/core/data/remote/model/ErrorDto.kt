package am.mojtaba.armengo.core.data.remote.model

import am.mojtaba.armengo.core.domain.model.Translate

data class ErrorDto(
    val id: String = "",
    val code: String = "",
    val translations: List<Translate> = emptyList()
)
