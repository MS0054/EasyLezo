package am.mojtaba.armengo.core.data.repository

import am.mojtaba.armengo.core.data.mapper.toDomain
import am.mojtaba.armengo.core.data.mapper.toDto
import am.mojtaba.armengo.core.data.remote.api.ReportApi
import am.mojtaba.armengo.core.domain.model.Report
import am.mojtaba.armengo.core.domain.repository.ReportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReportRepositoryImpl @Inject constructor(
    private val reportApi: ReportApi
) : ReportRepository {

    override fun observeReports(): Flow<List<Report>> = flow {
        try {
            val dtos = reportApi.getReports()
            emit(dtos.map { it.toDomain() })
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    override suspend fun addReport(report: Report): Result<Unit> {
        return try {
            reportApi.addReport(report.toDto())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteReport(reportId: String): Result<Unit> {
        return try {
            reportApi.deleteReport(reportId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
