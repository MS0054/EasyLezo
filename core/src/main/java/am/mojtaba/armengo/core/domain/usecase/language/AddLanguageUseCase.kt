package am.mojtaba.armengo.core.domain.usecase.language

import am.mojtaba.armengo.core.domain.model.Language
import am.mojtaba.armengo.core.domain.repository.LanguageRepository
import javax.inject.Inject

class AddLanguageUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    suspend operator fun invoke(language: Language) = languageRepository.addLanguageLocal(language)
}