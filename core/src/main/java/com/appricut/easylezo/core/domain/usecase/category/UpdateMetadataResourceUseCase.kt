package com.appricut.easylezo.core.domain.usecase.category

import android.content.Context
import android.widget.Toast
import com.appricut.easylezo.core.domain.model.Resource
import com.appricut.easylezo.core.domain.repository.MetadataRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UpdateMetadataResourceUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository
) {
    suspend operator fun invoke(resource: Resource) {
        val resources = metadataRepository.observeMetadata().map { it.resources }.first()
        val updatedList = resources.map { if (it.name == resource.name) resource else it }

        try {
            metadataRepository.updateMetadataResourcesServer(updatedList)
        } catch (e: Exception) {
        }
    }
}