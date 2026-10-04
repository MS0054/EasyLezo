package am.mojtaba.armengo.core.domain.usecase.metadata

import am.mojtaba.armengo.core.domain.model.Report
import am.mojtaba.armengo.core.domain.repository.ReportRepository
import javax.inject.Inject

class AddMetadataReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(report: Report) {
        reportRepository.addReport(report)
    }
}
