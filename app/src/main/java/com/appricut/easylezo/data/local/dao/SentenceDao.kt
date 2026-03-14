package com.appricut.easylezo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appricut.easylezo.data.local.entity.SentenceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SentenceDao {

    @Query("SELECT * FROM sentence WHERE categoryId = :categoryId ORDER BY `order` ASC")
    fun observeSentences(categoryId: String): Flow<List<SentenceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<SentenceEntity>)

    @Query("DELETE FROM sentence")
    suspend fun clearAll()
}