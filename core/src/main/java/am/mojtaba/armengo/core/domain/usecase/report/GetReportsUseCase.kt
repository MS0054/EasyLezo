package am.mojtaba.armengo.core.domain.usecase.report

import am.mojtaba.armengo.core.domain.model.Report
import am.mojtaba.armengo.core.domain.repository.ReportRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReportsUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    operator fun invoke(): Flow<List<Report>> {
        return reportRepository.observeReports()
    }
}
