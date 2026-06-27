package am.mojtaba.armengo.core.data.local.dao

import am.mojtaba.armengo.core.data.local.entity.WordEntity
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT * FROM word WHERE categoryId = :categoryId AND isDeleted = 0 ORDER BY `order` ASC")
    fun observe(categoryId: String): Flow<List<WordEntity?>?>
    @Query("SELECT * FROM word WHERE isSynced = 0")
    suspend fun observeUnsynced(): List<WordEntity>
    @Query("SELECT EXISTS(SELECT 1 FROM word WHERE isSynced = 0)")
    fun observeUnsyncedStatus(): Flow<Boolean>
    @Query("UPDATE word SET isSynced = 1 WHERE id IN (:ids)")
    suspend fun markAsSynced(ids: List<String>)
    @Query("UPDATE word SET isDeleted = 1, isSynced = 0 WHERE id = :id")
    suspend fun softDelete(id: String)
    @Upsert
    suspend fun upsertAll(words: List<WordEntity>)
    @Upsert
    suspend fun upsert(word: WordEntity)
    @Query("DELETE FROM word WHERE id NOT IN (:remainingIds)")
    suspend fun deleteOldIds(remainingIds: List<String>)
}