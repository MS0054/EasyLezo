package am.mojtaba.armengo.core.domain.usecase.sentence

import am.mojtaba.armengo.core.domain.repository.SentenceRepository
import javax.inject.Inject

class SyncSentenceFromServerUseCase @Inject constructor(
    private val sentenceRepository: SentenceRepository
) {
    suspend operator fun invoke(isForce: Boolean = false) {
        sentenceRepository.syncFromServer(isForce)
    }
}