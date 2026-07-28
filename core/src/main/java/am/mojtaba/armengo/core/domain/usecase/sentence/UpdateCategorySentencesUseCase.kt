package am.mojtaba.armengo.core.domain.usecase.sentence

import am.mojtaba.armengo.core.domain.repository.CategorySentenceRepository
import am.mojtaba.armengo.core.domain.repository.SentenceRepository
import javax.inject.Inject

class UpdateCategorySentencesUseCase @Inject constructor(
    private val categorySentenceRepository: CategorySentenceRepository
) {
    suspend operator fun invoke(categoryId: String, sentenceIds: List<String>) =
        categorySentenceRepository.updateCategorySentences(categoryId, sentenceIds)
}