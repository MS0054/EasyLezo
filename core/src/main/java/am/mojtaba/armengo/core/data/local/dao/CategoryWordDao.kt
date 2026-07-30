package am.mojtaba.armengo.core.data.local.dao

import am.mojtaba.armengo.core.data.local.entity.CategoryWordEntity
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryWordDao {
    @Upsert
    suspend fun upsertAll(crossRefs: List<CategoryWordEntity>)

    @Query("DELETE FROM category_word WHERE categoryId NOT IN (:remainingCategoryIds) AND wordId NOT IN (:remainingWordIds)")
    suspend fun deleteOld(remainingCategoryIds: List<String>, remainingWordIds: List<String>)

    @Query("DELETE FROM category_word")
    suspend fun clearAll()

    @Query("SELECT * FROM category_word WHERE isSynced = 0")
    suspend fun observeUnsynced(): List<CategoryWordEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM category_word WHERE isSynced = 0)")
    fun observeUnsyncedStatus(): Flow<Boolean>

    @Query("UPDATE category_word SET isSynced = 1 WHERE categoryId IN (:ids)")
    suspend fun markAsSynced(ids: List<String>)

    @Query("""
        UPDATE category_word 
        SET isDeleted = 1, isSynced = 0 
        WHERE categoryId = :categoryId AND isDeleted = 0
    """)
    suspend fun markAllAsDeleted(categoryId: String)

    @Query("DELETE FROM category_word WHERE categoryId = :categoryId")
    suspend fun deleteByCategoryId(categoryId: String)

    @Transaction
    suspend fun updateWordsForCategory(
        categoryId: String,
        entities: List<CategoryWordEntity>
    ) {
        markAllAsDeleted(categoryId)
        if (entities.isNotEmpty()) {
            upsertAll(entities)
        }
    }

    @Query(
        """
        UPDATE category_word 
        SET `order` = :newOrder, isSynced = 0
        WHERE categoryId = :categoryId AND wordId = :wordId AND isDeleted = 0
    """
    )
    suspend fun updateWordOrder(categoryId: String, wordId: String, newOrder: Int)

    @Transaction
    suspend fun updateCategoryWordsOrder(categoryId: String, orderedWordIds: List<String>) {
        orderedWordIds.forEachIndexed { index, wordId ->
            updateWordOrder(
                categoryId = categoryId,
                wordId = wordId,
                newOrder = index
            )
        }
    }

    @Query("DELETE FROM category_word WHERE isSynced = 1")
    suspend fun clearSyncedData()

    @Transaction
    suspend fun syncServerData(serverEntities: List<CategoryWordEntity>) {
        clearSyncedData()
        if (serverEntities.isNotEmpty()) {
            upsertAll(serverEntities)
        }
    }

    @Query("DELETE FROM category_word WHERE id NOT IN (:serverIds)")
    suspend fun deleteOldIds(serverIds: List<String>)

    @Transaction
    suspend fun overrideWithServerData(serverEntities: List<CategoryWordEntity>) {
        if (serverEntities.isEmpty()) {
            clearAll()
        } else {
            val serverIds = serverEntities.map { it.id }
            deleteOldIds(serverIds)
            upsertAll(serverEntities)
        }
    }
}
