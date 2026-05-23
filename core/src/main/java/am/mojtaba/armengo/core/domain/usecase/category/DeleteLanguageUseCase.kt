package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.model.Language
import am.mojtaba.armengo.core.domain.repository.LanguageRepository
import javax.inject.Inject

class DeleteLanguageUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    suspend operator fun invoke(language: Language) {
//        languageRepository.deleteLanguageLocal(language)
        try {
            languageRepository.deleteLanguageServer(language)
        } catch (e: Exception) {
            // Handle error or let WorkManager handle retry later
            e.printStackTrace()
        }
    }
}