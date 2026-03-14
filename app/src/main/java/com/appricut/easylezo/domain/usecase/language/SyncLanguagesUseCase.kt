package com.appricut.easylezo.domain.usecase.language

import com.appricut.easylezo.domain.repository.LanguageRepository
import javax.inject.Inject

class SyncLanguagesUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    suspend operator fun invoke() {
        languageRepository.syncLanguages()
    }
}