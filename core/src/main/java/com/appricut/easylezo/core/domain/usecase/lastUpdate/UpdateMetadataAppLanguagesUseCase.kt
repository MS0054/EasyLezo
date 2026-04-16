package com.appricut.easylezo.core.domain.usecase.lastUpdate

import com.appricut.easylezo.core.domain.model.AppLanguages
import com.appricut.easylezo.core.domain.model.LastUpdate
import com.appricut.easylezo.core.domain.repository.MetadataRepository
import com.appricut.easylezo.core.domain.usecase.metadata.GetLastUpdateUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateMetadataAppLanguagesUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository
) {
    suspend operator fun invoke(appLanguages: AppLanguages) {
        metadataRepository.updateMetadataAppLanguages(appLanguages)
    }
}