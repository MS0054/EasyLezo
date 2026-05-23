package am.mojtaba.armengo.core.domain.usecase.metadata

import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import kotlinx.coroutines.flow.Flow
import am.mojtaba.armengo.core.domain.model.Metadata
import javax.inject.Inject

class GetMetadataUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository
) {
    operator fun invoke(): Flow<Metadata> = metadataRepository.observeMetadata()
}