package com.appricut.easylezo.core.domain.usecase.category

import com.appricut.easylezo.core.domain.model.Category
import com.appricut.easylezo.core.domain.model.Language
import com.appricut.easylezo.core.domain.repository.CategoryRepository
import com.appricut.easylezo.core.domain.repository.LanguageRepository
import kotlinx.coroutines.flow.Flow
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