package am.mojtaba.armengo.core.data.remote.model

import am.mojtaba.armengo.core.domain.model.Translate

data class SentenceDto(
    val id: String = "",
    val categoryId: String = "",
    val level: String = "",
    val image: String = "",
    val order: Int = 0,
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
    val isSynced: Boolean = true,
    val isDeleted: Boolean = false,
    val translations: List<Translate> = emptyList()
)
