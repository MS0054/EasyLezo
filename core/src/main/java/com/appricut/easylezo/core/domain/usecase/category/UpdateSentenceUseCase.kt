package com.appricut.easylezo.core.domain.usecase.category

import com.appricut.easylezo.core.domain.model.Sentence
import com.appricut.easylezo.core.domain.repository.SentenceRepository
import javax.inject.Inject

class UpdateSentenceUseCase @Inject constructor(
    private val sentenceRepository: SentenceRepository
) {
    suspend operator fun invoke(sentence: Sentence) {
//        sentenceRepository.updateSentenceLocal(sentence)
        try {
            sentenceRepository.updateSentenceServer(sentence)
        } catch (e: Exception) {
            //  WorkManager
        }
    }

}