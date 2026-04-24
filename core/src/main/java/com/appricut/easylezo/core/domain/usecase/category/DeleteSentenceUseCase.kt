package com.appricut.easylezo.core.domain.usecase.category

import com.appricut.easylezo.core.domain.model.Sentence
import com.appricut.easylezo.core.domain.repository.SentenceRepository
import javax.inject.Inject

class DeleteSentenceUseCase @Inject constructor(
    private val sentenceRepository: SentenceRepository
) {
    suspend operator fun invoke(sentence: Sentence) {
//        sentenceRepository.deleteSentenceLocal(sentence)
        try {
            sentenceRepository.deleteSentenceServer(sentence)
        } catch (e: Exception) {
            //  WorkManager
        }
    }

}