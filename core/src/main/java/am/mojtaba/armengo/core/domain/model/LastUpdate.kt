package am.mojtaba.armengo.core.domain.model

data class LastUpdate(
    val id: Long = 0L,
    var language: Long = 0L,
    var category: Long = 0L,
    var sentence: Long = 0L,
    var categorySentence: Long = 0L,
    var word: Long = 0L,
    var categoryWord: Long = 0L,
    var user: Long = 0L,
    var image: Long = 0L,
    var existNewUserData: Boolean = false,
    var existNewLanguageData: Boolean = false,
    var existNewCategoryData: Boolean = false,
    var existNewSentenceData: Boolean = false,
    var existNewCategorySentenceData: Boolean = false,
    var existNewWordData: Boolean = false,
    var existNewCategoryWordData: Boolean = false,
    var existNewImageData: Boolean = false
) {
    fun mergeWith(current: LastUpdate, currentTime: Long) {
        user = if (existNewUserData) currentTime else current.user
        category = if (existNewCategoryData) currentTime else current.category
        sentence = if (existNewSentenceData) currentTime else current.sentence
        categorySentence = if (existNewCategorySentenceData) currentTime else current.categorySentence
        word = if (existNewWordData) currentTime else current.word
        categoryWord = if (existNewCategoryWordData) currentTime else current.categoryWord
        language = if (existNewLanguageData) currentTime else current.language
        image = if (existNewImageData) currentTime else current.image
    }
}