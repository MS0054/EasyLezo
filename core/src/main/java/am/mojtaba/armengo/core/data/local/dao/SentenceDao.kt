package am.mojtaba.armengo.core.data.local.dao

import am.mojtaba.armengo.core.data.local.entity.LanguageEntity
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import am.mojtaba.armengo.core.data.local.entity.SentenceEntity
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SentenceDao {

    @Query("SELECT * FROM sentence WHERE categoryId = :categoryId AND isDeleted = 0 ORDER BY `order` ASC")
    fun observe(categoryId: String): Flow<List<SentenceEntity?>?>

    @Query("SELECT * FROM sentence WHERE isSynced = 0")
    suspend fun observeUnsynced(): List<SentenceEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM sentence WHERE isSynced = 0)")
    fun observeUnsyncedStatus(): Flow<Boolean>

    @Query("UPDATE sentence SET isSynced = 1 WHERE id IN (:ids)")
    suspend fun markAsSynced(ids: List<String>)

    @Query("UPDATE sentence SET isDeleted = 1, isSynced = 0 WHERE id = :id")
    suspend fun softDelete(id: String)

    @Upsert
    suspend fun upsertAll(sentences: List<SentenceEntity>)

    @Upsert
    suspend fun upsert(sentence: SentenceEntity)

    @Query("DELETE FROM sentence WHERE id NOT IN (:remainingIds)")
    suspend fun deleteOldIds(remainingIds: List<String>)
}