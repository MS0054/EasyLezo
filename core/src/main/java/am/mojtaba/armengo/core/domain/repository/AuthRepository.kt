package am.mojtaba.armengo.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUp(email: String, password: String, displayName: String): Result<String>
    suspend fun signIn(email: String, password: String): Result<String>
    suspend fun signInWithGoogle(idToken: String): Result<String>
    suspend fun signOut()
//    suspend fun checkAdmin()
    suspend fun isCurrentUserAdmin(): Boolean
    fun getCurrentUserUid(): String?

}