package com.appricut.easylezo.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.appricut.easylezo.core.data.local.entity.CategoryEntity
import com.appricut.easylezo.core.data.local.entity.LanguageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LanguageDao {

    @Query("SELECT * FROM languages ORDER BY `order` ASC")
    fun observeLanguages(): Flow<List<LanguageEntity?>?>

    @Upsert
    suspend fun upsertAll(languages: List<LanguageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(languages: List<LanguageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(language: LanguageEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(language: LanguageEntity)

    @Delete
    suspend fun delete(language: LanguageEntity)

    @Query("DELETE FROM languages")
    suspend fun clearAll()

    @Query("SELECT MAX(`order`) FROM languages")
    suspend fun getMaxOrder(): Int?

    @Query("DELETE FROM languages WHERE id NOT IN (:remainingIds)")
    suspend fun deleteOldIds(remainingIds: List<String>)
}