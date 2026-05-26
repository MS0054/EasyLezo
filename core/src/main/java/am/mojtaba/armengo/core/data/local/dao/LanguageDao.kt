package am.mojtaba.armengo.core.data.local.dao

import am.mojtaba.armengo.core.data.local.entity.CategoryEntity
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import am.mojtaba.armengo.core.data.local.entity.LanguageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LanguageDao {

    @Query("SELECT * FROM languages WHERE isDeleted = 0 ORDER BY `order` ASC")
    fun observe(): Flow<List<LanguageEntity?>?>

    @Query("SELECT * FROM languages WHERE isSynced = 0")
    suspend fun observeUnsynced(): List<LanguageEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM languages WHERE isSynced = 0)")
    fun observeUnsyncedStatus(): Flow<Boolean>

    @Query("UPDATE languages SET isSynced = 1 WHERE id IN (:ids)")
    suspend fun markAsSynced(ids: List<String>)

    @Query("UPDATE languages SET isDeleted = 1, isSynced = 0 WHERE id = :id")
    suspend fun softDelete(id: String)

    @Upsert
    suspend fun upsertAll(languages: List<LanguageEntity>)

    @Upsert
    suspend fun upsert(language: LanguageEntity)

    @Query("SELECT MAX(`order`) FROM languages")
    suspend fun getMaxOrder(): Int?

    @Query("DELETE FROM languages WHERE id NOT IN (:remainingIds)")
    suspend fun deleteOldIds(remainingIds: List<String>)
}