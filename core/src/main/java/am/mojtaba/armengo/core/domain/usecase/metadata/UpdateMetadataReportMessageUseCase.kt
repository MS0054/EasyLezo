package am.mojtaba.armengo.core.domain.usecase.metadata

import am.mojtaba.armengo.core.domain.model.ReportMessage
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UpdateMetadataReportMessageUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository
) {
    suspend operator fun invoke(reportMessage: ReportMessage) {
        val reportMessages = metadataRepository.observeMetadata().map { it.reportMessages }.first()
        val updatedList = reportMessages.map { if (it.key == reportMessage.key || (it.id != 0L && it.id == reportMessage.id)) reportMessage else it }

        try {
            metadataRepository.updateMetadataReportMessagesServer(updatedList)
        } catch (e: Exception) {
        }
    }
}
