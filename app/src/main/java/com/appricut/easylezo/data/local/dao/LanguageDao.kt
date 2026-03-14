package com.appricut.easylezo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appricut.easylezo.data.local.entity.LanguageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LanguageDao {

    @Query("SELECT * FROM languages ORDER BY `order` ASC")
    fun observeLanguages(): Flow<List<LanguageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<LanguageEntity>)

    @Query("DELETE FROM languages")
    suspend fun clearAll()
}