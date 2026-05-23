package am.mojtaba.armengo.core.domain.usecase.lastUpdate

import am.mojtaba.armengo.core.domain.model.AppLanguages
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import javax.inject.Inject

class UpdateMetadataAppLanguagesUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository
) {
    suspend operator fun invoke(appLanguages: AppLanguages) {
        metadataRepository.updateMetadataAppLanguages(appLanguages)
        metadataRepository.syncMetadata(true)
    }
}