package am.mojtaba.armengo.core.domain.usecase.word

import am.mojtaba.armengo.core.domain.repository.CategoryWordRepository
import javax.inject.Inject

class SortCategoryWordUseCase @Inject constructor(
    private val repository: CategoryWordRepository
) {
    suspend operator fun invoke(categoryId: String, orderedWordIds: List<String>) {
        repository.sortCategoryWordsLocal(categoryId, orderedWordIds)
    }
}
