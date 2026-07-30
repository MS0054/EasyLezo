package am.mojtaba.armengo.core.domain.usecase.word

import am.mojtaba.armengo.core.domain.model.Word
import am.mojtaba.armengo.core.domain.repository.AppLanguagesRepository
import am.mojtaba.armengo.core.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetCategoryWordsUseCase @Inject constructor(
    private val wordRepository: WordRepository,
    private val appLanguagesRepository: AppLanguagesRepository
) {
    operator fun invoke(categoryId: String): Flow<List<Word>> {
        val wordsFlow = wordRepository.observe(categoryId)
        val appLanguagesFlow = appLanguagesRepository.observeAppLanguages()

        return wordsFlow.combine(appLanguagesFlow) { words, languages ->
            words.map { word ->
                val fromText = word.translations.find { it.language == languages.from }?.text ?: ""
                val toText = word.translations.find { it.language == languages.to }?.text ?: ""

                word.copy(
                    fromText = fromText,
                    toText = toText
                )
            }
        }
    }
}
