package com.appricut.easylezo.core.domain.usecase.category

import com.appricut.easylezo.core.domain.model.Language
import com.appricut.easylezo.core.domain.repository.LanguageRepository
import javax.inject.Inject

class UpdateLanguageUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    suspend operator fun invoke(language: Language) {
//         languageRepository.updateLanguageLocal(language)
        try {
            languageRepository.updateLanguageServer(language)
        } catch (e: Exception) {
            // اگر آفلاین بود، اینجا مدیریت می‌شود (مثلاً با WorkManager برای بعدا)
        }
    }

}