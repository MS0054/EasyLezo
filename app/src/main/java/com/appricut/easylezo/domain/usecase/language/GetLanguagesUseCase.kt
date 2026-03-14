package com.appricut.easylezo.domain.usecase.language

import com.appricut.easylezo.domain.model.Language
import com.appricut.easylezo.domain.repository.LanguageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

class GetLanguagesUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    operator fun invoke(): Flow<List<Language>> = languageRepository.observeLanguages()
}