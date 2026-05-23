package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.manager.SyncManager
import javax.inject.Inject

class SyncCategoryUseCase @Inject constructor(
    private val syncManager: SyncManager
) {
    operator fun invoke(workerTag: String) {
        syncManager.syncCategory(workerTag)
    }
}