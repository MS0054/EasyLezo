package am.mojtaba.armengo.core.domain.usecase.sentence

import am.mojtaba.armengo.core.domain.model.Sentence
import am.mojtaba.armengo.core.domain.repository.AppLanguagesRepository
import am.mojtaba.armengo.core.domain.repository.SentenceRepository
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetCategorySentencesUseCase @Inject constructor(
    private val sentenceRepository: SentenceRepository,
    private val appLanguagesRepository: AppLanguagesRepository
) {
    operator fun invoke(categoryId: String): Flow<List<Sentence>> {
        val sentencesFlow = sentenceRepository.observe(categoryId)
        val appLanguagesFlow = appLanguagesRepository.observeAppLanguages()


        return sentencesFlow.combine(appLanguagesFlow) { sentences, languages ->
            sentences.map { sentence ->
                val fromText = sentence.translations.find { it.language == languages.from }?.text ?: ""
                val toText = sentence.translations.find { it.language == languages.to }?.text ?: ""
                val phonetic = sentence.translations.find { it.language == languages.to }?.phonetic ?: ""
                Log.i("DATAS: ","Sentences: $toText  \n")

                sentence.copy(
                    fromText = fromText,
                    toText = toText,
                    phonetic = phonetic
                )
            }
        }
    }
}