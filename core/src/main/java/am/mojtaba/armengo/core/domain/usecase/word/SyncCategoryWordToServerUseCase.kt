package am.mojtaba.armengo.core.domain.usecase.word

import am.mojtaba.armengo.core.domain.manager.SyncManager
import javax.inject.Inject

class SyncCategoryWordToServerUseCase @Inject constructor(
    private val syncManager: SyncManager
) {
    operator fun invoke(workerTag: String) = syncManager.syncCategoryWordToServer(workerTag)
}
