package com.appricut.easylezo.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appricut.easylezo.core.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user LIMIT 1")
    fun observeUser(): Flow<UserEntity?>

    @Query("SELECT * FROM user")
    fun observeUsers(): Flow<List<UserEntity?>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)

//    @Query("DELETE FROM user WHERE uid = :uid")
//    suspend fun delete(user: UserEntity)

    @Query("DELETE FROM user")
    suspend fun clearAll()
}