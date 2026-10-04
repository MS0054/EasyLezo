package am.mojtaba.armengo.core.domain.usecase.report

import am.mojtaba.armengo.core.domain.repository.ReportRepository
import javax.inject.Inject

class DeleteReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(reportId: String): Result<Unit> {
        return reportRepository.deleteReport(reportId)
    }
}
