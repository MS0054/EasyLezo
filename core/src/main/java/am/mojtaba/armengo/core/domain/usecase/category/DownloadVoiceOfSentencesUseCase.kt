package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.model.Sentence
import am.mojtaba.armengo.core.domain.repository.SentenceRepository
import javax.inject.Inject

class DownloadVoiceOfSentencesUseCase @Inject constructor(
    private val sentenceRepository: SentenceRepository
) {
    suspend operator fun invoke(sentences: List<Sentence>) {

        try {
            sentenceRepository.downloadVoice(sentences)
        } catch (e: Exception) {
            //  WorkManager
        }
    }

}

