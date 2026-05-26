package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.manager.SyncManager
import javax.inject.Inject

class SyncLanguageToServerUseCase @Inject constructor(
    private val syncManager: SyncManager
) {
    operator fun invoke(workerTag: String) = syncManager.syncLanguageToServer(workerTag)
}