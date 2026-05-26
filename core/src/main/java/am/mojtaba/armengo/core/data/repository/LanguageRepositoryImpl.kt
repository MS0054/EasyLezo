package am.mojtaba.armengo.core.data.repository
import am.mojtaba.armengo.core.data.local.dao.LanguageDao
import am.mojtaba.armengo.core.data.mapper.toDomain
import am.mojtaba.armengo.core.data.mapper.toDto
import am.mojtaba.armengo.core.data.mapper.toEntity
import am.mojtaba.armengo.core.data.remote.api.LanguageApi
import am.mojtaba.armengo.core.domain.model.Language
import am.mojtaba.armengo.core.domain.repository.LanguageRepository
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.map

@Singleton
class LanguageRepositoryImpl @Inject constructor(
    private val metadataRepository: MetadataRepository,
    private val languageDao: LanguageDao,
    private val languageApi: LanguageApi,
) : LanguageRepository {

    override fun observe(): Flow<List<Language>> {
        return languageDao.observe()
            .map { list -> list?.map { it?.toDomain() ?: Language() } ?: emptyList()   }
    }
    override fun observeUnsyncedStatus(): Flow<Boolean> {
        return languageDao.observeUnsyncedStatus()
    }
    override suspend fun syncFromServer(isForce: Boolean ) {
        val metadata = metadataRepository.observeMetadata().first()
        if (metadata.lastUpdate.existNewLanguageData || isForce) {
            val newLanguages = languageApi.getLanguages()

            languageDao.upsertAll(newLanguages.map { it.toEntity() })
            languageDao.deleteOldIds(newLanguages.map { it.id })

            metadata.lastUpdate.existNewLanguageData = false
            metadataRepository.clearAndInsert(metadata)
        }
    }
    override suspend fun addLanguageLocal(language: Language) {
        return languageDao.upsert(language.toEntity().copy(isSynced = false))
    }
    override suspend fun updateLanguageLocal(language: Language) {
        return languageDao.upsert(language.toEntity().copy(isSynced = false))
    }
    override suspend fun deleteLanguageLocal(id: String) {
        return languageDao.softDelete(id)
    }
    override suspend fun sortLanguageLocal(languages: List<Language>) {
        return languageDao.upsertAll(languages.map { it.toEntity().copy(isSynced = false) })
    }
}
