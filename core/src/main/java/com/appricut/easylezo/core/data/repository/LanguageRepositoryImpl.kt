package com.appricut.easylezo.core.data.repository
import android.util.Log
import com.appricut.easylezo.core.data.local.dao.LanguageDao
import com.appricut.easylezo.core.data.local.dao.MetadataDao
import com.appricut.easylezo.core.data.mapper.toDomain
import com.appricut.easylezo.core.data.mapper.toDto
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
import kotlin.collections.map

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

    override suspend fun syncLanguages( isForce: Boolean ) {

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
        return languageDao.insert(language.toEntity())
    }
    override suspend fun addLanguageServer(language: Language) {
        languageApi.addLanguage(language.toDto())
        syncLanguages(true)
    }
    override suspend fun updateLanguageLocal(language: Language) {
        return languageDao.update(language.toEntity())
    }
    override suspend fun updateLanguageServer(language: Language) {
        languageApi.updateLanguage(language.toDto())
        syncLanguages(true)
    }
    override suspend fun deleteLanguageLocal(language: Language) {
        return languageDao.delete(language.toEntity())
    }
    override suspend fun deleteLanguageServer(language: Language) {
        languageApi.deleteLanguage(language.toDto())
        syncLanguages(true)
    }
    override suspend fun sortLanguageLocal(languages: List<Language>) {
        return languageDao.insertAll(languages.map { it.toEntity() })
    }
    override suspend fun sortLanguageServer(languages: List<Language>) {
        languageApi.sortLanguages(languages.map { it.toDto() })
        syncLanguages(true)
    }
}
