package com.appricut.easylezo.core.data.repository
import android.util.Log
import com.appricut.easylezo.core.data.local.dao.LanguageDao
import com.appricut.easylezo.core.data.local.dao.MetadataDao
import com.appricut.easylezo.core.data.mapper.toDomain
import com.appricut.easylezo.core.data.mapper.toEntity
import com.appricut.easylezo.core.data.remote.api.LanguageApi
import com.appricut.easylezo.core.domain.model.Language
import com.appricut.easylezo.core.domain.repository.LanguageRepository
import com.appricut.easylezo.core.domain.repository.MetadataRepository
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
            .map { list -> list?.map { it?.toDomain() ?: Language() } ?: emptyList()   }
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
