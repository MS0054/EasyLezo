package am.mojtaba.armengo.core.domain.usecase.lastUpdate

import am.mojtaba.armengo.core.domain.model.UpdateInfo
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import javax.inject.Inject

class UpdateMetadataUpdateInfoUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository
) {
    suspend operator fun invoke(updateInfo: UpdateInfo) {
        metadataRepository.updateMetadataUpdateInfo(updateInfo)
    }
}