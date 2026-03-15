package com.appricut.easylezo.core.domain.usecase.language

import com.appricut.easylezo.core.domain.repository.LanguageRepository
import javax.inject.Inject

class SyncLanguagesUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    suspend operator fun invoke() {
        languageRepository.syncLanguages()
    }
}