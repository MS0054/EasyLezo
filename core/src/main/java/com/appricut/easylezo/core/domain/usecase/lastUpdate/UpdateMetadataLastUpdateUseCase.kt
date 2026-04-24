package com.appricut.easylezo.core.domain.usecase.lastUpdate

import com.appricut.easylezo.core.domain.model.LastUpdate
import com.appricut.easylezo.core.domain.repository.MetadataRepository
import com.appricut.easylezo.core.domain.usecase.metadata.GetLastUpdateUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateMetadataLastUpdateUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository,
    private val getLastUpdateUseCase: GetLastUpdateUseCase
) {
    suspend operator fun invoke(lastUpdate: LastUpdate) {
        val currentTime = System.currentTimeMillis() / 1000
        val currentLastUpdate = getLastUpdateUseCase().first()

        lastUpdate.mergeWith(currentLastUpdate, currentTime)
        metadataRepository.updateMetadataLastUpdate(lastUpdate)
    }
}