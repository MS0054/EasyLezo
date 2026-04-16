package com.appricut.easylezo.core.domain.usecase.category

import com.appricut.easylezo.core.domain.model.Language
import com.appricut.easylezo.core.domain.repository.LanguageRepository
import javax.inject.Inject

class SortLanguageUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    suspend operator fun invoke(languages: List<Language>) {
//        languageRepository.sortLanguageLocal(languages)
        try {
            languageRepository.sortLanguageServer(languages)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}