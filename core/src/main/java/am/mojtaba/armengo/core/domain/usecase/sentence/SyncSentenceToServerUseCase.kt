package am.mojtaba.armengo.core.domain.usecase.sentence

import am.mojtaba.armengo.core.domain.manager.SyncManager
import am.mojtaba.armengo.core.domain.repository.SentenceRepository
import javax.inject.Inject

class SyncSentenceToServerUseCase @Inject constructor(
    private val syncManager: SyncManager)
{
    operator fun invoke(workerTag: String) = syncManager.syncSentenceToServer(workerTag)
}