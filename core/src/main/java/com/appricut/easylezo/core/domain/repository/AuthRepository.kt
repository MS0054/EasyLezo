package com.appricut.easylezo.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun checkAdmin()
    fun isAdmin(): Flow<Boolean?>
    fun getUid(): String
    fun signOut()
}