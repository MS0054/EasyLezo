package com.appricut.easylezo.core.domain.usecase.appLanguages

import com.appricut.easylezo.core.domain.model.AppLanguages
import com.appricut.easylezo.core.domain.repository.MetadataRepository
import com.appricut.easylezo.core.domain.usecase.language.GetLanguagesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMetadataAppLanguagesUseCase @Inject constructor(
    private val getLanguagesUseCase: GetLanguagesUseCase,
    private val metadataRepository: MetadataRepository
) {
    operator fun invoke(): Flow<AppLanguages> {
//        val serverDataFlow = flow {
//            emit(metadataRepository.observeMetadataServer())
//        }
        val localAppLanguages = metadataRepository.observeMetadata()

        val databaseFlow = getLanguagesUseCase()

        return databaseFlow.combine(localAppLanguages) { languagesList, serverMetadata ->
            AppLanguages().apply {
                languages = languagesList
                from = serverMetadata.appLanguages.from
                to = serverMetadata.appLanguages.to
                app = serverMetadata.appLanguages.app
            }
        }
    }
}
