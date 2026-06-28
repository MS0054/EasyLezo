package am.mojtaba.armengo.core.domain.usecase.word

import am.mojtaba.armengo.core.domain.manager.SyncManager
import javax.inject.Inject

class SyncWordToServerUseCase @Inject constructor(
    private val syncManager: SyncManager)
{
    operator fun invoke(workerTag: String) = syncManager.syncWordToServer(workerTag)
}