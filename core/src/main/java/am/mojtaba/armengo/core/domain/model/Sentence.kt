package am.mojtaba.armengo.core.domain.model

data class Sentence(
    val id: String = "",
    val level: String = "",
    val image: String = "",
    val fromText:String = "",
    val toText: String= "",
    val order: Int = 0,
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
    val voiceUrl: String = "",
    val hasVoice: Boolean = false,
    val isSynced: Boolean = true,
    val isDeleted: Boolean = false,
    val translations: List<Translate> = emptyList(),
)



