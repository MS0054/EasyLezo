package am.mojtaba.armengo.core.domain.usecase.metadata

import am.mojtaba.armengo.core.domain.model.ReportStatusType
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UpdateMetadataReportStatusTypeUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository
) {
    suspend operator fun invoke(reportStatusType: ReportStatusType) {
        val reportStatusTypes = metadataRepository.observeMetadata().map { it.reportStatusTypes }.first()
        val updatedList = reportStatusTypes.map { if (it.key == reportStatusType.key || (it.id != 0L && it.id == reportStatusType.id)) reportStatusType else it }

        try {
            metadataRepository.updateMetadataReportStatusTypesServer(updatedList)
        } catch (e: Exception) {
        }
    }
}
