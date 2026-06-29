package am.mojtaba.armengo.core.domain.usecase.language

import am.mojtaba.armengo.core.domain.repository.LanguageRepository
import javax.inject.Inject

class DeleteLanguageUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    suspend operator fun invoke(id: String) = languageRepository.deleteLanguageLocal(id)

}