package am.mojtaba.armengo.core.domain.usecase.metadata

import am.mojtaba.armengo.core.domain.model.ReportType
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UpdateMetadataReportTypeUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository
) {
    suspend operator fun invoke(reportType: ReportType) {
        val reportTypes = metadataRepository.observeMetadata().map { it.reportTypes }.first()
        val updatedList = reportTypes.map { if (it.key == reportType.key || (it.id != 0L && it.id == reportType.id)) reportType else it }

        try {
            metadataRepository.updateMetadataReportTypesServer(updatedList)
        } catch (e: Exception) {
        }
    }
}
