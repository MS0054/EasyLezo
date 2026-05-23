package am.mojtaba.armengo.core.domain.usecase.sentence

import am.mojtaba.armengo.core.domain.repository.SentenceRepository
import javax.inject.Inject

class SyncSentencesUseCase @Inject constructor(
    private val sentenceRepository: SentenceRepository
) {
    suspend operator fun invoke(isForce: Boolean = false) {
        sentenceRepository.syncSentences(isForce)
    }
}