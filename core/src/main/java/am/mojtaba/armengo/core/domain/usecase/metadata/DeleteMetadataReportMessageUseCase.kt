package am.mojtaba.armengo.core.domain.usecase.metadata

import am.mojtaba.armengo.core.domain.model.ReportMessage
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeleteMetadataReportMessageUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository
) {
    suspend operator fun invoke(reportMessage: ReportMessage) {
        val reportMessages = metadataRepository.observeMetadata().map { it.reportMessages }.first()

        val updatedList = reportMessages.filterNot { it.key == reportMessage.key || (it.id != "" && it.id == reportMessage.id) }
        try {
            metadataRepository.updateMetadataReportMessagesServer(updatedList)
        } catch (e: Exception) {
        }
    }
}
