package am.mojtaba.armengo.core.domain.usecase.metadata

import am.mojtaba.armengo.core.domain.model.LastUpdate
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetLastUpdateUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository
) {
    operator fun invoke(): Flow<LastUpdate> {
      val metadata = metadataRepository.observeMetadata()
      return metadata.map { it.lastUpdate }
    }
}