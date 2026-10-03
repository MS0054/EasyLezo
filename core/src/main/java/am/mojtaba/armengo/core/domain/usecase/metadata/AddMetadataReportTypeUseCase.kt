package am.mojtaba.armengo.core.domain.usecase.metadata

import android.content.Context
import android.widget.Toast
import am.mojtaba.armengo.core.domain.model.ReportType
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AddMetadataReportTypeUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository,
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(reportType: ReportType) {
        val reportTypes = metadataRepository.observeMetadata().map { it.reportTypes }.first()

        if (reportTypes.any { it.key == reportType.key }) {
            Toast.makeText(context, "This report type key already exists", Toast.LENGTH_LONG).show()
        } else {
            val newReportTypes = reportTypes + reportType
            try {
                metadataRepository.updateMetadataReportTypesServer(newReportTypes)
            } catch (e: Exception) {
            }
        }
    }
}
