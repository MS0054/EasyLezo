package am.mojtaba.armengo.core.domain.usecase.metadata

import android.content.Context
import android.widget.Toast
import am.mojtaba.armengo.core.domain.model.ReportStatusType
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AddMetadataReportStatusTypeUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository,
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(reportStatusType: ReportStatusType) {
        val reportStatusTypes = metadataRepository.observeMetadata().map { it.reportStatusTypes }.first()

        if (reportStatusTypes.any { it.key == reportStatusType.key }) {
            Toast.makeText(context, "This status type key already exists", Toast.LENGTH_LONG).show()
        } else {
            val newReportStatusTypes = reportStatusTypes + reportStatusType
            try {
                metadataRepository.updateMetadataReportStatusTypesServer(newReportStatusTypes)
            } catch (e: Exception) {
            }
        }
    }
}
