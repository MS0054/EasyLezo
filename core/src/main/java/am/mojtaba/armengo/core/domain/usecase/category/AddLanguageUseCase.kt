package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.model.Language
import am.mojtaba.armengo.core.domain.repository.LanguageRepository
import javax.inject.Inject

class AddLanguageUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    suspend operator fun invoke(language: Language) {
         languageRepository.addLanguageLocal(language)
        try {
            languageRepository.addLanguageServer(language)
        } catch (e: Exception) {
            // اگر آفلاین بود، اینجا مدیریت می‌شود (مثلاً با WorkManager برای بعدا)
        }
    }

}