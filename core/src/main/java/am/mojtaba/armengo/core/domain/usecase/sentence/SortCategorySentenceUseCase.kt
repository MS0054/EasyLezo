package am.mojtaba.armengo.core.domain.usecase.sentence

import am.mojtaba.armengo.core.domain.repository.CategorySentenceRepository
import javax.inject.Inject

class SortCategorySentenceUseCase @Inject constructor(
    private val categorySentenceUseCase: CategorySentenceRepository
) {
    suspend operator fun invoke(categoryId: String, orderedSentenceIds: List<String>) = categorySentenceUseCase.sortCategorySentencesLocal(categoryId, orderedSentenceIds)
}