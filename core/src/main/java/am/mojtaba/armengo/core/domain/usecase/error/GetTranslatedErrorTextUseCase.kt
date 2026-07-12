package am.mojtaba.armengo.core.domain.usecase.error

import am.mojtaba.armengo.core.domain.repository.AppLanguagesRepository
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GetTranslatedErrorTextUseCase @Inject constructor(
        private val metadataRepository: MetadataRepository,
        private val appLanguagesRepository: AppLanguagesRepository
    ) {
        suspend operator fun invoke(code: String): String {

            val currentAppLanguage = appLanguagesRepository.observeAppLanguages().firstOrNull()?.app.orEmpty()
            val metadata = metadataRepository.observeMetadata().firstOrNull()
            val targetError = metadata?.errors?.find { it.code == code }

            if (targetError == null || currentAppLanguage.isEmpty()) {
                return "Unknown Error"
            }

            val translation = targetError.translations.find {
                it.language.equals(currentAppLanguage, ignoreCase = true)
            }

            return translation?.text ?: "Failed to fetch error text"
        }
    }