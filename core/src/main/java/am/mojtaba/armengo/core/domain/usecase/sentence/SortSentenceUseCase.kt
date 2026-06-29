package am.mojtaba.armengo.core.domain.usecase.sentence

import am.mojtaba.armengo.core.domain.model.Sentence
import am.mojtaba.armengo.core.domain.repository.SentenceRepository
import javax.inject.Inject

class SortSentenceUseCase @Inject constructor(
    private val sentenceRepository: SentenceRepository
) {
    suspend operator fun invoke(sentences: List<Sentence>) = sentenceRepository.sortSentenceLocal(sentences)
}