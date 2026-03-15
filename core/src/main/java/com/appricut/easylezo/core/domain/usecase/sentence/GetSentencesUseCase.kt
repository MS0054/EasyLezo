package com.appricut.easylezo.core.domain.usecase.sentence

import android.util.Log
import com.appricut.easylezo.core.domain.model.Sentence
import com.appricut.easylezo.core.domain.repository.AppLanguagesRepository
import com.appricut.easylezo.core.domain.repository.SentenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class GetSentencesUseCase @Inject constructor(
    private val sentenceRepository: SentenceRepository,
    private val appLanguagesRepository: AppLanguagesRepository
) {
    operator fun invoke(categoryId: String): Flow<List<Sentence>> {
        val sentencesFlow = sentenceRepository.observeSentences(categoryId)
        val appLanguagesFlow = appLanguagesRepository.observeAppLanguages()

        return sentencesFlow.combine(appLanguagesFlow) { sentences, languages ->
            sentences.map { sentence ->
                val fromText = sentence.translations.find { it.language == languages.from }?.text ?: ""
                val toText = sentence.translations.find { it.language == languages.to }?.text ?: ""
                sentence.copy(
                    fromText = fromText,
                    toText = toText,
                    translations = emptyList()
                )
            }
        }
    }
}