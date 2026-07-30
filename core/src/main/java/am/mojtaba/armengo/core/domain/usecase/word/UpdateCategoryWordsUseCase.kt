package am.mojtaba.armengo.core.domain.usecase.word

import am.mojtaba.armengo.core.domain.repository.CategoryWordRepository
import javax.inject.Inject

class UpdateCategoryWordsUseCase @Inject constructor(
    private val repository: CategoryWordRepository
) {
    suspend operator fun invoke(categoryId: String, newWordIds: List<String>) =
        repository.updateCategoryWords(categoryId, newWordIds)
}
