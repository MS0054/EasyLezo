package com.appricut.easylezo.domain.usecase.metadata

import com.appricut.easylezo.domain.model.Metadata
import com.appricut.easylezo.domain.repository.MetadataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMetadataUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository
) {
    operator fun invoke(): Flow<Metadata> = metadataRepository.observeMetadata()
}