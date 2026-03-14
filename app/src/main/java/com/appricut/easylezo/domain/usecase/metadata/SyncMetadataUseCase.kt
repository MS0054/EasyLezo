package com.appricut.easylezo.domain.usecase.metadata

import com.appricut.easylezo.domain.repository.MetadataRepository
import javax.inject.Inject

class SyncMetadataUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository
) {
    suspend operator fun invoke() {
        metadataRepository.syncMetadata()
    }
}