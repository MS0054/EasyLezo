package com.appricut.easylezo.core.domain.usecase.metadata

import com.appricut.easylezo.core.domain.repository.MetadataRepository
import javax.inject.Inject

class SyncMetadataUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository
) {
    suspend operator fun invoke() {
        metadataRepository.syncMetadata()
    }
}