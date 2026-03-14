package com.appricut.easylezo.domain.usecase.sentence

import com.appricut.easylezo.domain.repository.SentenceRepository
import javax.inject.Inject

class SyncSentencesUseCase @Inject constructor(
    private val sentenceRepository: SentenceRepository
) {
    suspend operator fun invoke() {
        sentenceRepository.syncSentences()
    }
}