package com.appricut.easylezo.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appricut.easylezo.core.data.local.entity.AppLanguagesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppLanguagesDao {

    @Query("SELECT * FROM appLanguages")
    fun observeAppLanguages(): Flow<AppLanguagesEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(appLanguages: AppLanguagesEntity)

    @Query("DELETE FROM appLanguages")
    suspend fun clearAll()
}