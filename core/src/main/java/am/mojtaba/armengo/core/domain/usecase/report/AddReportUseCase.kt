package am.mojtaba.armengo.core.domain.usecase.report

import am.mojtaba.armengo.core.domain.model.Report
import am.mojtaba.armengo.core.domain.repository.ReportRepository
import javax.inject.Inject

class AddReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(report: Report): Result<Unit> {
        return reportRepository.addReport(report)
    }
}
