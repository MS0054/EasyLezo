package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.model.Category
import am.mojtaba.armengo.core.domain.repository.CategoryRepository
import javax.inject.Inject

class AddCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    suspend operator fun invoke(category: Category) {
        categoryRepository.addCategoryLocal(category)
    }

}