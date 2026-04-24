package com.appricut.easylezo.core.data.repository

import com.appricut.easylezo.core.data.remote.api.AuthApi
import com.appricut.easylezo.core.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {

    private val _isAdmin = MutableStateFlow<Boolean?>(null)


    override suspend fun checkAdmin() {
        _isAdmin.value = authApi.isCurrentUserAdmin()
    }

    override fun getUid(): String = authApi.currentUserUid()

    override fun signOut() {
        authApi.signOut()
    }

    override fun isAdmin(): Flow<Boolean?> = _isAdmin
}