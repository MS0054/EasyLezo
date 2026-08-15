package am.mojtaba.armengo.core.domain.usecase.metadata

import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import javax.inject.Inject

class SyncImageFromServerUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return metadataRepository.syncImage()
    }
}