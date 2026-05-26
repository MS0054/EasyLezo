package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.manager.SyncManager
import javax.inject.Inject

class SyncCategoryToServerUseCase @Inject constructor(
    private val syncManager: SyncManager
) {
    operator fun invoke(workerTag: String) = syncManager.syncCategoryToServer(workerTag)
}