package am.mojtaba.armengo.core.domain.usecase.metadata

import am.mojtaba.armengo.core.domain.model.Error
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeleteMetadataErrorUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository
) {
    suspend operator fun invoke(error: Error) {
        val errors = metadataRepository.observeMetadata().map { it.errors }.first()

        val updatedList = errors.filterNot { it.code == error.code }
        try {
            metadataRepository.updateMetadataErrorsServer(updatedList)
        } catch (e: Exception) {
        }
    }
}