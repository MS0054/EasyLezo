package com.appricut.easylezo.core.data.repository

import android.util.Log
import com.appricut.easylezo.core.data.local.dao.AppLanguagesDao
import com.appricut.easylezo.core.data.local.dao.MetadataDao
import com.appricut.easylezo.core.data.local.entity.MetadataEntity
import com.appricut.easylezo.core.data.mapper.toDomain
import com.appricut.easylezo.core.data.mapper.toDto
import com.appricut.easylezo.core.data.mapper.toEntity
import com.appricut.easylezo.core.data.remote.api.MetadataApi
import com.appricut.easylezo.core.data.remote.model.MetadataDto
import com.appricut.easylezo.core.domain.model.AppLanguages
import com.appricut.easylezo.core.domain.model.Language
import com.appricut.easylezo.core.domain.model.LastUpdate
import com.appricut.easylezo.core.domain.model.Metadata
import com.appricut.easylezo.core.domain.model.Resource
import com.appricut.easylezo.core.domain.model.Settings
import com.appricut.easylezo.core.domain.repository.AppLanguagesRepository
import com.appricut.easylezo.core.domain.repository.MetadataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.map

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

    override suspend fun observeMetadataServer(): Metadata {
        return metadataApi.getMetadata().toDomain()
    }

    override suspend fun syncMetadata() {

        val newMetadata = metadataApi.getMetadata()
        val currentMetadata = metadataDao.observeMetadata().first() ?: MetadataEntity()

        newMetadata.lastUpdate.apply {
            existNewLanguageData = language > currentMetadata.lastUpdate.language
            existNewCategoryData = category > currentMetadata.lastUpdate.category
            existNewSentenceData = sentence > currentMetadata.lastUpdate.sentence
            existNewUserData = user > currentMetadata.lastUpdate.user
        }

        val appLanguages = appLanguagesRepository.observeAppLanguages().firstOrNull()
        if (appLanguages == null || appLanguages.isDefault) {
            appLanguagesRepository.updateLocalAppLanguages(newMetadata.appLanguages.toDomain())
        }
        clearAndInsert(newMetadata.toDomain())
    }


    override suspend fun clearAndInsert(metadata: Metadata) {
        metadataDao.clearAndInsert(metadata.toEntity())
    }

    override suspend fun updateMetadataLastUpdate(lastUpdate: LastUpdate) {
        metadataApi.updateMetadataLastUpdate(lastUpdate.toDto())
    }

    override suspend fun updateMetadataAppLanguages(appLanguages: AppLanguages) {
        metadataApi.updateMetadataAppLanguages(appLanguages.toDto())
        syncMetadata()
    }

    override suspend fun updateMetadataSettings(settings: Settings) {
        metadataApi.updateMetadataSettings(settings.toDto())
        syncMetadata()
    }

    override suspend fun updateMetadataResourcesServer(resources: List<Resource>) {
        metadataApi.updateMetadataResources(resources.map { it.toDto() })
        syncMetadata()
    }

}
