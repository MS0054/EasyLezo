package am.mojtaba.armengo.core.data.local.dao

import am.mojtaba.armengo.core.data.local.entity.CategorySentenceEntity
import am.mojtaba.armengo.core.data.local.entity.SentenceEntity
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CategorySentenceDao {
    @Upsert
    suspend fun upsertAll(crossRefs: List<CategorySentenceEntity>)

    @Query("DELETE FROM category_sentence WHERE categoryId NOT IN (:remainingCategoryIds) AND sentenceId NOT IN (:remainingSentenceIds)")
    suspend fun deleteOld(remainingCategoryIds: List<String>, remainingSentenceIds: List<String>)

    @Query("DELETE FROM category_sentence")
    suspend fun clearAll()

    @Query("SELECT * FROM category_sentence WHERE isSynced = 0")
    suspend fun observeUnsynced(): List<CategorySentenceEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM category_sentence WHERE isSynced = 0)")
    fun observeUnsyncedStatus(): Flow<Boolean>

    @Query("UPDATE category_sentence SET isSynced = 1 WHERE categoryId IN (:ids)")
    suspend fun markAsSynced(ids: List<String>)

    @Query("""
        UPDATE category_sentence 
        SET isDeleted = 1, isSynced = 0 
        WHERE categoryId = :categoryId AND isDeleted = 0
    """)
    suspend fun markAllAsDeleted(categoryId: String)

    @Query("DELETE FROM category_sentence WHERE categoryId = :categoryId")
    suspend fun deleteByCategoryId(categoryId: String)

    @Transaction
    suspend fun updateSentencesForCategory(
        categoryId: String,
        entities: List<CategorySentenceEntity>
    ) {
        // ابتدا تمام روابط قبلی را Soft Delete می‌کنیم تا اگر جابه‌جایی/حذفی بوده در Sync مشخص شود
        markAllAsDeleted(categoryId)

        // سپس لیست جدید (با orderIndexهای درست و isDeleted = false) را جایگزین می‌کنیم
        if (entities.isNotEmpty()) {
            upsertAll(entities)
        }
    }


    @Query(
        """
        UPDATE category_sentence 
        SET `order` = :newOrder, isSynced = 0
        WHERE categoryId = :categoryId AND sentenceId = :sentenceId AND isDeleted = 0
    """
    )
    suspend fun updateSentenceOrder(categoryId: String, sentenceId: String, newOrder: Int)

    @Transaction
    suspend fun updateCategorySentencesOrder(categoryId: String, orderedSentenceIds: List<String>) {
        orderedSentenceIds.forEachIndexed { index, sentenceId ->
            updateSentenceOrder(
                categoryId = categoryId,
                sentenceId = sentenceId,
                newOrder = index
            )
        }
    }


    @Query("DELETE FROM category_sentence WHERE isSynced = 1")
    suspend fun clearSyncedData()

    @Transaction
    suspend fun syncServerData(serverEntities: List<CategorySentenceEntity>) {
        // ۱. فقط داده‌هایی که از قبل با موفقیت سینک شده بودن رو پاک می‌کنیم
        // (داده‌هایی که isSynced = 0 هستند و کاربر آفلاین تغییر داده باقی می‌مانند)
        clearSyncedData()

        // ۲. داده‌های جدید سرور (که همگی isSynced = true دارند) را درج می‌کنیم
        if (serverEntities.isNotEmpty()) {
            upsertAll(serverEntities)
        }
    }

    @Query("DELETE FROM category_sentence WHERE id NOT IN (:serverIds)")
    suspend fun deleteOldIds(serverIds: List<String>)

    @Transaction
    suspend fun overrideWithServerData(serverEntities: List<CategorySentenceEntity>) {
        if (serverEntities.isEmpty()) {
            clearAll()
        } else {
            val serverIds = serverEntities.map { it.id }
            deleteOldIds(serverIds)
            upsertAll(serverEntities)
        }
    }
}
