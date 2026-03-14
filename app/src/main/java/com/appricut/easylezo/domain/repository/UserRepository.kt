package com.appricut.easylezo.domain.repository

import com.appricut.easylezo.domain.model.AppLanguages
import com.appricut.easylezo.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun observeUser(): Flow<User>
    suspend fun syncUser(uid: String)
    suspend fun updateServerUserAppLanguages(appLanguages: AppLanguages)

}