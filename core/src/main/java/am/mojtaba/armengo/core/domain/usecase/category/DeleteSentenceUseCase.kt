package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.model.Sentence
import am.mojtaba.armengo.core.domain.repository.SentenceRepository
import javax.inject.Inject

class DeleteSentenceUseCase @Inject constructor(
    private val sentenceRepository: SentenceRepository
) {
    suspend operator fun invoke(sentence: Sentence) {
//        sentenceRepository.deleteSentenceLocal(sentence)
        try {
            sentenceRepository.deleteSentenceServer(sentence)
        } catch (e: Exception) {
            //  WorkManager
        }
    }

}