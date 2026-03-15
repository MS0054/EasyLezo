package com.appricut.easylezo.core.data.repository

import android.util.Log
import com.appricut.easylezo.core.data.local.dao.AppLanguagesDao
import com.appricut.easylezo.core.data.local.dao.MetadataDao
import com.appricut.easylezo.core.data.local.entity.MetadataEntity
import com.appricut.easylezo.core.data.mapper.toDomain
import com.appricut.easylezo.core.data.mapper.toEntity
import com.appricut.easylezo.core.data.remote.api.MetadataApi
import com.appricut.easylezo.core.domain.model.Metadata
import com.appricut.easylezo.core.domain.repository.AppLanguagesRepository
import com.appricut.easylezo.core.domain.repository.MetadataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MetadataRepositoryImpl @Inject constructor(
    private val appLanguagesRepository: AppLanguagesRepository,
    private val metadataDao: MetadataDao,
    private val metadataApi: MetadataApi
) : MetadataRepository {

    override fun observeMetadata(): Flow<Metadata> {
        return metadataDao.observeMetadata()
            .map { metadata -> metadata?.toDomain() ?: Metadata() }
    }

    override suspend fun syncMetadata() {

        val newMetadata = metadataApi.getMetadata()
        val currentMetadata = metadataDao.observeMetadata().firstOrNull()
        if (currentMetadata != null) {
            newMetadata.lastUpdate.apply {
                existNewLanguageData = language > currentMetadata.lastUpdate.language
                existNewCategoryData = category > currentMetadata.lastUpdate.category
                existNewSentenceData = sentence > currentMetadata.lastUpdate.sentence
                existNewUserData = user > currentMetadata.lastUpdate.user
            }
        }

        val appLanguages = appLanguagesRepository.observeAppLanguages().firstOrNull()

        if (appLanguages == null || appLanguages.isDefault){
            appLanguagesRepository.updateLocalAppLanguages(newMetadata.settings.appLanguages.toDomain())
        }

        clearAndInsert(newMetadata.toDomain())


    }

    override suspend fun clearAndInsert(metadata: Metadata) {
        metadataDao.clearAndInsert(metadata.toEntity())
    }
}
