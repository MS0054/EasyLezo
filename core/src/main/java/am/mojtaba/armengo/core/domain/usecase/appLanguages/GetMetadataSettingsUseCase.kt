package am.mojtaba.armengo.core.domain.usecase.appLanguages

import am.mojtaba.armengo.core.domain.model.Settings
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMetadataSettingsUseCase @Inject constructor(

    private val metadataRepository: MetadataRepository
) {
     operator fun invoke(): Flow<Settings> {
         return metadataRepository.observeMetadata().map { it.settings } }
}
