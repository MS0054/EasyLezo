package com.appricut.easylezo.core.domain.usecase.appLanguages

import com.appricut.easylezo.core.domain.model.UpdateInfo
import com.appricut.easylezo.core.domain.repository.MetadataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMetadataUpdateInfoUseCase @Inject constructor(

    private val metadataRepository: MetadataRepository
) {
     operator fun invoke(): Flow<UpdateInfo> {
         return metadataRepository.observeMetadata().map { it.updateInfo } }
}
