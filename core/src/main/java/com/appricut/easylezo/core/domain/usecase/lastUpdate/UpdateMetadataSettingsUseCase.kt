package com.appricut.easylezo.core.domain.usecase.lastUpdate

import com.appricut.easylezo.core.domain.model.Settings
import com.appricut.easylezo.core.domain.repository.MetadataRepository
import javax.inject.Inject

class UpdateMetadataSettingsUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository
) {
    suspend operator fun invoke(settings: Settings) {
        metadataRepository.updateMetadataSettings(settings)
    }
}