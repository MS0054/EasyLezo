package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveUnSyncedCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return categoryRepository.observeUnsyncedStatus()
    }
}