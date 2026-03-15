package com.appricut.easylezo.core.domain.repository

import com.appricut.easylezo.core.domain.model.AppLanguages
import com.appricut.easylezo.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun observeUser(): Flow<User>
    suspend fun syncUser(uid: String)
    suspend fun updateServerUserAppLanguages(appLanguages: AppLanguages)

}