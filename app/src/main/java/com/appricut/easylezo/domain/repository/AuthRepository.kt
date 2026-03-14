package com.appricut.easylezo.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun checkAdmin()
    fun isAdmin(): Flow<Boolean?>
    fun getUid(): String
}