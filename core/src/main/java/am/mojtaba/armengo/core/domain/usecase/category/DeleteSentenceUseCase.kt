package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.repository.SentenceRepository
import javax.inject.Inject

class DeleteSentenceUseCase @Inject constructor(
    private val sentenceRepository: SentenceRepository
) {
    suspend operator fun invoke(id: String) = sentenceRepository.deleteSentenceLocal(id)
}