package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.repository.CategoryRepository
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(id: String) {
        categoryRepository.deleteCategoryLocal(id)
    }
}