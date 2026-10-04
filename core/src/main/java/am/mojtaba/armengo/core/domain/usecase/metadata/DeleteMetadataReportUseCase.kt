package am.mojtaba.armengo.core.domain.usecase.metadata

import am.mojtaba.armengo.core.domain.repository.ReportRepository
import javax.inject.Inject

class DeleteMetadataReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(reportId: String) {
        reportRepository.deleteReport(reportId)
    }
}
