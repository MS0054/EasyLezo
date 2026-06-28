package am.mojtaba.armengo.core.domain.usecase.word

import am.mojtaba.armengo.core.domain.repository.WordRepository
import javax.inject.Inject

class SyncWordFromServerUseCase @Inject constructor(
    private val wordRepository: WordRepository
) {
    suspend operator fun invoke(isForce: Boolean = false): Result<Unit> = wordRepository.syncFromServer(isForce)
}