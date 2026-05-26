package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.repository.SentenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveUnSyncedSentenceUseCase @Inject constructor(
    private val sentenceRepository: SentenceRepository
) {
    operator fun invoke(): Flow<Boolean> = sentenceRepository.observeUnsynced()
}