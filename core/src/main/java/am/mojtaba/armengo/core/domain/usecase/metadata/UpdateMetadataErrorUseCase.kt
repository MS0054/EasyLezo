package am.mojtaba.armengo.core.domain.usecase.metadata

import am.mojtaba.armengo.core.domain.model.Error
import am.mojtaba.armengo.core.domain.model.Resource
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UpdateMetadataErrorUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository
) {
    suspend operator fun invoke(error: Error) {
        val errors = metadataRepository.observeMetadata().map { it.errors }.first()
        val updatedList = errors.map { if (it.code == error.code) error else it }

        try {
            metadataRepository.updateMetadataErrorsServer(updatedList)
        } catch (e: Exception) {
        }
    }
}