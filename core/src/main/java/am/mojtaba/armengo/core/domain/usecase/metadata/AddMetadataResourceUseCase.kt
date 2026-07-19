package am.mojtaba.armengo.core.domain.usecase.metadata

import android.content.Context
import android.widget.Toast
import am.mojtaba.armengo.core.domain.model.Resource
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AddMetadataResourceUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository,
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(resource: Resource) {
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