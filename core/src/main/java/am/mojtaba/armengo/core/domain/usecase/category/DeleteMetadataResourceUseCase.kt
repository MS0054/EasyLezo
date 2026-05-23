package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.model.Resource
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeleteMetadataResourceUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository
) {
    suspend operator fun invoke(resource: Resource) {
        val resources = metadataRepository.observeMetadata().map { it.resources }.first()

        val updatedList = resources.filterNot { it.name == resource.name }
        try {
            metadataRepository.updateMetadataResourcesServer(updatedList)
        } catch (e: Exception) {
        }
    }
}