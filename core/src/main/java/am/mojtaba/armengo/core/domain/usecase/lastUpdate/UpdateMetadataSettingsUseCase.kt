package am.mojtaba.armengo.core.domain.usecase.lastUpdate

import am.mojtaba.armengo.core.domain.model.Settings
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import javax.inject.Inject

class UpdateMetadataSettingsUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository
) {
    suspend operator fun invoke(settings: Settings) {
        metadataRepository.updateMetadataSettings(settings)
    }
}