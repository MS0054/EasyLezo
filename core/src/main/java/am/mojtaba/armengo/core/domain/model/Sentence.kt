package am.mojtaba.armengo.core.domain.model

data class Sentence(
    val id: String = "",
    val categoryId: String = "",
    val level: String = "",
    val image: String = "",
    val fromText:String = "",
    val toText: String= "",
    val order: Int = 0,
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
    val translations: List<Translate> = emptyList(),
)



