package com.appricut.easylezo.core.domain.usecase.category

import com.appricut.easylezo.core.domain.model.Sentence
import com.appricut.easylezo.core.domain.repository.SentenceRepository
import javax.inject.Inject

class SortSentenceUseCase @Inject constructor(
    private val sentenceRepository: SentenceRepository
) {
    suspend operator fun invoke(sentences: List<Sentence>) {
//        sentenceRepository.sortSentenceLocal(sentences)
        try {
            sentenceRepository.sortSentenceServer(sentences)
        } catch (e: Exception) {
//            add workManager
        }
    }

}