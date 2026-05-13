package com.appricut.easylezo.core.domain.usecase.lastUpdate

import com.appricut.easylezo.core.domain.model.UpdateInfo
import com.appricut.easylezo.core.domain.repository.MetadataRepository
import javax.inject.Inject

class UpdateMetadataUpdateInfoUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository
) {
    suspend operator fun invoke(updateInfo: UpdateInfo) {
        metadataRepository.updateMetadataUpdateInfo(updateInfo)
    }
}