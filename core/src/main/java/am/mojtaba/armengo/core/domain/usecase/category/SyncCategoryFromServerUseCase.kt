package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.repository.CategoryRepository
import javax.inject.Inject

class SyncCategoryFromServerUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(isForce: Boolean = false) {
        categoryRepository.syncLocal(isForce)
    }
}