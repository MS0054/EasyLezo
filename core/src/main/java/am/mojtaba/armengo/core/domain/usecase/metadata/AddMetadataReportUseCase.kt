package am.mojtaba.armengo.core.domain.usecase.metadata

import android.content.Context
import android.widget.Toast
import am.mojtaba.armengo.core.domain.model.Report
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AddMetadataReportUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository,
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(report: Report) {
        val reports = metadataRepository.observeMetadata().map { it.reports }.first()

        if (reports.any { it.key == report.key }) {
            Toast.makeText(context, "This report key already exists", Toast.LENGTH_LONG).show()
        } else {
            val newReports = reports + report
            try {
                metadataRepository.updateMetadataReportsServer(newReports)
            } catch (e: Exception) {
            }
        }
    }
}
