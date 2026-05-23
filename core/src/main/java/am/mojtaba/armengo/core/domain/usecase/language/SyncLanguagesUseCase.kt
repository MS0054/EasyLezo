package am.mojtaba.armengo.core.domain.usecase.language

import am.mojtaba.armengo.core.domain.repository.LanguageRepository
import javax.inject.Inject

class SyncLanguagesUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    suspend operator fun invoke( isForce: Boolean = false ) {
        languageRepository.syncLanguages( isForce )
    }
}