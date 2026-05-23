package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.model.Language
import am.mojtaba.armengo.core.domain.repository.LanguageRepository
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