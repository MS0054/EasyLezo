package am.mojtaba.armengo.core.domain.usecase.language

import am.mojtaba.armengo.core.domain.model.Language
import am.mojtaba.armengo.core.domain.repository.LanguageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLanguagesUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    operator fun invoke(): Flow<List<Language>> = languageRepository.observeLanguages()
}