package am.mojtaba.armengo.core.data.remote.api

import am.mojtaba.armengo.core.domain.model.ReportDto

interface ReportApi {
    suspend fun getReports(): List<ReportDto>
    suspend fun addReport(report: ReportDto)
    suspend fun deleteReport(reportId: String)
}
