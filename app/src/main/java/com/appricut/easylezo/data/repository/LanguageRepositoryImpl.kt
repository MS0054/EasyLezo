package com.appricut.easylezo.data.repository
import android.util.Log
import com.appricut.easylezo.data.local.dao.LanguageDao
import com.appricut.easylezo.data.local.dao.MetadataDao
import com.appricut.easylezo.data.mapper.toDomain
import com.appricut.easylezo.data.mapper.toEntity
import com.appricut.easylezo.data.remote.api.LanguageApi
import com.appricut.easylezo.domain.model.Language
import com.appricut.easylezo.domain.repository.LanguageRepository
import com.appricut.easylezo.domain.repository.MetadataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageRepositoryImpl @Inject constructor(
    private val metadataRepository: MetadataRepository,
    private val languageDao: LanguageDao,
    private val languageApi: LanguageApi,
) : LanguageRepository {

    override fun observeLanguages(): Flow<List<Language>> {
        return languageDao.observeLanguages()
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun syncLanguages() {

        val metadata = metadataRepository.observeMetadata().first()
        if (metadata.lastUpdate.existNewLanguageData) {
            val newLanguages = languageApi.getLanguages()
            languageDao.clearAll()
            languageDao.insertAll(newLanguages.map { it.toEntity() })

            metadata.lastUpdate.existNewLanguageData = false
            metadataRepository.clearAndInsert(metadata)
        }
    }
}
