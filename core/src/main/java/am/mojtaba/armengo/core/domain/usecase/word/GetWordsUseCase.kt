package am.mojtaba.armengo.core.domain.usecase.word

import am.mojtaba.armengo.core.domain.model.Word
import am.mojtaba.armengo.core.domain.repository.AppLanguagesRepository
import am.mojtaba.armengo.core.domain.repository.WordRepository
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class GetWordsUseCase @Inject constructor(
    private val wordRepository: WordRepository,
    private val appLanguagesRepository: AppLanguagesRepository
) {
    operator fun invoke(categoryId: String): Flow<List<Word>> {
        val wordsFlow = wordRepository.observe(categoryId)
        val appLanguagesFlow = appLanguagesRepository.observeAppLanguages()

        runBlocking {
            val www = wordRepository.observe(categoryId).first()
            Log.i("TOTO", "invoke : $www")
        }



        return wordsFlow.combine(appLanguagesFlow) { words, languages ->
            words.map { word ->
                Log.i("TOTO", "invoke : $words")
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