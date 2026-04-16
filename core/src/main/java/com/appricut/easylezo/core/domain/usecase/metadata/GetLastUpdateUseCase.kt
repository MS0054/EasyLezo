package com.appricut.easylezo.core.domain.usecase.metadata

import com.appricut.easylezo.core.domain.model.LastUpdate
import com.appricut.easylezo.core.domain.model.Metadata
import com.appricut.easylezo.core.domain.repository.MetadataRepository
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