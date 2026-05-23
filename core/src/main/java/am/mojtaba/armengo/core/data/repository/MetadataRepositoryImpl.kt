package am.mojtaba.armengo.core.data.repository

import am.mojtaba.armengo.core.data.local.dao.MetadataDao
import am.mojtaba.armengo.core.data.local.entity.MetadataEntity
import am.mojtaba.armengo.core.data.mapper.toDomain
import am.mojtaba.armengo.core.data.mapper.toDto
import am.mojtaba.armengo.core.data.mapper.toEntity
import am.mojtaba.armengo.core.data.remote.api.MetadataApi
import am.mojtaba.armengo.core.domain.model.AppLanguages
import am.mojtaba.armengo.core.domain.model.LastUpdate
import am.mojtaba.armengo.core.domain.model.Resource
import am.mojtaba.armengo.core.domain.model.Settings
import am.mojtaba.armengo.core.domain.model.UpdateInfo
import am.mojtaba.armengo.core.domain.model.Metadata
import am.mojtaba.armengo.core.domain.repository.AppLanguagesRepository
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import android.util.Log
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

    override suspend fun syncMetadata(isForce: Boolean) {

        val newMetadata = metadataApi.getMetadata()
        val currentMetadata = metadataDao.observeMetadata().first() ?: MetadataEntity()

        newMetadata.lastUpdate.apply {
            existNewLanguageData = language > currentMetadata.lastUpdate.language
            existNewCategoryData = category > currentMetadata.lastUpdate.category
            existNewSentenceData = sentence > currentMetadata.lastUpdate.sentence
            existNewUserData = user > currentMetadata.lastUpdate.user
        }

        val appLanguages = appLanguagesRepository.observeAppLanguages().firstOrNull()
        if (appLanguages == null || appLanguages.isDefault || isForce) {
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

    override suspend fun updateMetadataUpdateInfo(updateInfo: UpdateInfo) {
        metadataApi.updateMetadataUpdateInfo(updateInfo.toDto())
        syncMetadata()
    }

    override suspend fun updateMetadataResourcesServer(resources: List<Resource>) {
        metadataApi.updateMetadataResources(resources.map { it.toDto() })
        syncMetadata()
    }

}
