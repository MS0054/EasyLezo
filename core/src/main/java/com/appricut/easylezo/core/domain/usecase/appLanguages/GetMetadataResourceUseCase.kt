package com.appricut.easylezo.core.domain.usecase.appLanguages

import androidx.compose.ui.res.integerResource
import com.appricut.easylezo.core.domain.model.Resource
import com.appricut.easylezo.core.domain.model.Settings
import com.appricut.easylezo.core.domain.repository.MetadataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMetadataResourcesUseCase @Inject constructor(

    private val metadataRepository: MetadataRepository
) {
     operator fun invoke(): Flow<List<Resource>> {
        return metadataRepository.observeMetadata().map { it.resources }
    }
}
