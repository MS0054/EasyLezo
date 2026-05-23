package am.mojtaba.armengo.core.domain.model

data class LastUpdate(
    val id: Long = 0L,
    var language: Long = 0L,
    var category: Long = 0L,
    var sentence: Long = 0L,
    var user: Long = 0L,
    var existNewUserData: Boolean = false,
    var existNewLanguageData: Boolean = false,
    var existNewCategoryData: Boolean = false,
    var existNewSentenceData: Boolean = false,
) {
    fun mergeWith(current: LastUpdate, currentTime: Long) {
        user = if (existNewUserData) currentTime else current.user
        category = if (existNewCategoryData) currentTime else current.category
        sentence = if (existNewSentenceData) currentTime else current.sentence
        language = if (existNewLanguageData) currentTime else current.language
    }
}