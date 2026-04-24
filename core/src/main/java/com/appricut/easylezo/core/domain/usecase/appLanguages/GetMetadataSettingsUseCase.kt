package com.appricut.easylezo.core.domain.usecase.appLanguages

import com.appricut.easylezo.core.domain.model.Settings
import com.appricut.easylezo.core.domain.repository.MetadataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMetadataSettingsUseCase @Inject constructor(

    private val metadataRepository: MetadataRepository
) {
     operator fun invoke(): Flow<Settings> {
         return metadataRepository.observeMetadata().map { it.settings } }
}
