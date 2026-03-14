package com.appricut.easylezo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.appricut.easylezo.data.local.entity.MetadataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MetadataDao {

    @Query("SELECT * FROM metadata LIMIT 1")
    fun observeMetadata(): Flow<MetadataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(metadata: MetadataEntity)

    @Query("DELETE FROM metadata")
    suspend fun clearAll()

    @Transaction
    suspend fun clearAndInsert(metadata: MetadataEntity) {
        clearAll()
        insertAll(metadata)
    }
}