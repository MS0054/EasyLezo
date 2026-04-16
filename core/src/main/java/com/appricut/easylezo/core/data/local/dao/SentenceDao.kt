package com.appricut.easylezo.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.appricut.easylezo.core.data.local.entity.CategoryEntity
import com.appricut.easylezo.core.data.local.entity.SentenceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SentenceDao {

    @Query("SELECT * FROM sentence WHERE categoryId = :categoryId ORDER BY `order` ASC")
    fun observeSentences(categoryId: String): Flow<List<SentenceEntity?>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<SentenceEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sentence: SentenceEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(sentence: SentenceEntity)

    @Delete
    suspend fun delete(sentence: SentenceEntity)

    @Query("DELETE FROM sentence")
    suspend fun clearAll()
}