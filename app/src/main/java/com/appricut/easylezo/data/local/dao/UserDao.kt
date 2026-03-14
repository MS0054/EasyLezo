package com.appricut.easylezo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appricut.easylezo.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user LIMIT 1")
    fun observeUser(): Flow<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(user: UserEntity)

    @Query("DELETE FROM user")
    suspend fun clearAll()
}