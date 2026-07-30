package am.mojtaba.armengo.core.data.repository

import am.mojtaba.armengo.core.data.local.dao.CategoryWordDao
import am.mojtaba.armengo.core.data.local.dao.WordDao
import am.mojtaba.armengo.core.data.mapper.toDomain
import am.mojtaba.armengo.core.data.mapper.toDto
import am.mojtaba.armengo.core.data.mapper.toEntity
import am.mojtaba.armengo.core.data.remote.api.CategoryWordApi
import am.mojtaba.armengo.core.data.remote.api.WordApi
import am.mojtaba.armengo.core.domain.model.Word
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import am.mojtaba.armengo.core.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordRepositoryImpl @Inject constructor(
    private val metadataRepository: MetadataRepository,
    private val wordDao: WordDao,
    private val wordApi: WordApi,
    private val categoryWordDao: CategoryWordDao,
    private val categoryWordApi: CategoryWordApi

): WordRepository {

    override fun observe(categoryId: String): Flow<List<Word>> = wordDao.observe(categoryId).map { list -> list?.map { it?.toDomain() ?: Word()  } ?: emptyList()  }
    override fun observe(): Flow<List<Word>> = wordDao.observe().map { list -> list?.map { it?.toDomain() ?: Word() } ?: emptyList() }
    override fun observeUnsynced(): Flow<Boolean> = wordDao.observeUnsyncedStatus()

    override suspend fun syncFromServer(isForce: Boolean): Result<Unit> {
        return try {
            val metadata = metadataRepository.observeMetadata().first()
            if (metadata.lastUpdate.existNewWordData || metadata.lastUpdate.existNewCategoryWordData || isForce) {
                val newWords = wordApi.getWords()
                val newCategoryWords = categoryWordApi.getCategoryWords()

                wordDao.upsertAll(newWords.map { it.toEntity() })
                wordDao.deleteOldIds(newWords.map { it.id })

                categoryWordDao.clearAll()
                categoryWordDao.upsertAll(newCategoryWords.map { it.toEntity() })

                val updatedMetadata = metadata.copy(lastUpdate = metadata.lastUpdate.copy(
                    existNewWordData = false,
                    existNewCategoryWordData = false
                ))
                metadataRepository.clearAndInsert(updatedMetadata)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addWordLocal(word: Word) = wordDao.upsert(word.toEntity().copy(isSynced = false))
    override suspend fun updateWordLocal(word: Word) = wordDao.upsert(word.toEntity().copy(isSynced = false))
    override suspend fun deleteWordLocal(id: String) = wordDao.softDelete(id)
    override suspend fun sortWordLocal(words: List<Word>) = wordDao.upsertAll(words.map { it.toEntity().copy(isSynced = false) })
    override suspend fun downloadVoice(words: List<Word>) = wordApi.downloadVoices(words.map { it.toDto() })


}
