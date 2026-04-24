package com.appricut.easylezo.core.domain.repository

import com.appricut.easylezo.core.domain.model.AppLanguages
import com.appricut.easylezo.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun observeUser(): Flow<User>
    fun observeUsers(): Flow<List<User>>
    suspend fun syncUser(uid: String)
    suspend fun searchUser(name: String?, email: String?): List<User>
    suspend fun syncUsers(limit: Long)
    suspend fun updateUser(user: User)
    suspend fun updateUserAppLanguagesServer(appLanguages: AppLanguages)

}