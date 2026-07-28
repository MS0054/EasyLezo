package am.mojtaba.armengo.core.domain.usecase.sentence

import am.mojtaba.armengo.core.domain.repository.CategorySentenceRepository
import am.mojtaba.armengo.core.domain.repository.SentenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveUnSyncedCategorySentenceUseCase @Inject constructor(
    private val categorySentenceRepository: CategorySentenceRepository
) {
    operator fun invoke(): Flow<Boolean> = categorySentenceRepository.observeUnsynced()
}