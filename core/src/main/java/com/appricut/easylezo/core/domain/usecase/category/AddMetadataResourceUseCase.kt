package com.appricut.easylezo.core.domain.usecase.category

import android.content.Context
import android.widget.Toast
import com.appricut.easylezo.core.domain.model.Resource
import com.appricut.easylezo.core.domain.repository.MetadataRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AddMetadataResourceUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository
) {
    suspend operator fun invoke(context: Context,resource: Resource) {
        val resources = metadataRepository.observeMetadata().map { it.resources }.first()

        if (resources.any { it.name == resource.name }) {
            Toast.makeText(context, "This resource already exists", Toast.LENGTH_LONG).show()
        }else{
            val newResources = resources + resource
            try {
                metadataRepository.updateMetadataResourcesServer(newResources)
            } catch (e: Exception) {
            }
        }
    }
}