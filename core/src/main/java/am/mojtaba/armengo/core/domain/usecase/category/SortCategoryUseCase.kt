package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.model.Category
import am.mojtaba.armengo.core.domain.repository.CategoryRepository
import javax.inject.Inject

class SortCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(categories: List<Category>) {
        categoryRepository.sortCategoryLocal(categories)
    }
}