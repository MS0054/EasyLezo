package am.mojtaba.armengo.core.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import am.mojtaba.armengo.core.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category WHERE isDeleted = 0 ORDER BY `order` ASC")
    fun observe(): Flow<List<CategoryEntity?>?>
    @Query("SELECT * FROM category WHERE isSynced = 0")
    suspend fun observeUnsynced(): List<CategoryEntity>
    @Query("SELECT EXISTS(SELECT 1 FROM category WHERE isSynced = 0)")
    fun observeUnsyncedStatus(): Flow<Boolean>
    @Query("UPDATE category SET isSynced = 1 WHERE id IN (:ids)")
    suspend fun markAsSynced(ids: List<String>)
    @Query("UPDATE category SET isDeleted = 1, isSynced = 0 WHERE id = :id")
    suspend fun softDelete(id: String)
    @Upsert
    suspend fun upsertAll(categories: List<CategoryEntity>)
    @Upsert
    suspend fun upsert(category: CategoryEntity)
    @Query("DELETE FROM category WHERE id NOT IN (:remainingIds)")
    suspend fun deleteOldIds(remainingIds: List<String>)

}