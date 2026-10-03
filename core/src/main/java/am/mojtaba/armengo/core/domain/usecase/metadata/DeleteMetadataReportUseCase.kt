package am.mojtaba.armengo.core.domain.usecase.metadata

import am.mojtaba.armengo.core.domain.model.Report
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeleteMetadataReportUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository
) {
    suspend operator fun invoke(report: Report) {
        val reports = metadataRepository.observeMetadata().map { it.reports }.first()

        val updatedList = reports.filterNot { it.key == report.key || (it.id != 0L && it.id == report.id) }
        try {
            metadataRepository.updateMetadataReportsServer(updatedList)
        } catch (e: Exception) {
        }
    }
}
