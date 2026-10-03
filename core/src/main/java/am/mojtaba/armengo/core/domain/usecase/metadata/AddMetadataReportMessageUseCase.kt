package am.mojtaba.armengo.core.domain.usecase.metadata

import android.content.Context
import android.widget.Toast
import am.mojtaba.armengo.core.domain.model.ReportMessage
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AddMetadataReportMessageUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository,
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(reportMessage: ReportMessage) {
        val reportMessages = metadataRepository.observeMetadata().map { it.reportMessages }.first()

        if (reportMessages.any { it.key == reportMessage.key }) {
            Toast.makeText(context, "This report message key already exists", Toast.LENGTH_LONG).show()
        } else {
            val newReportMessages = reportMessages + reportMessage
            try {
                metadataRepository.updateMetadataReportMessagesServer(newReportMessages)
            } catch (e: Exception) {
            }
        }
    }
}
