package am.mojtaba.armengo.core.domain.repository

import am.mojtaba.armengo.core.domain.model.Report
import kotlinx.coroutines.flow.Flow

interface ReportRepository {
    fun observeReports(): Flow<List<Report>>
    suspend fun addReport(report: Report): Result<Unit>
    suspend fun deleteReport(reportId: String): Result<Unit>
}
