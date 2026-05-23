package am.mojtaba.armengo.core.domain.usecase.metadata

import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import javax.inject.Inject

class SyncMetadataUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository
) {
    suspend operator fun invoke() {
        metadataRepository.syncMetadata()
    }
}