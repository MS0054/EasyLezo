package com.appricut.easylezo.domain.usecase

import com.appricut.easylezo.data.repository.AuthRepository2
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepo: AuthRepository2
) {

    suspend fun signUp(email: String, password: String, displayName: String) =
        authRepo.signUpWithEmail(email, password, displayName)

    suspend fun signIn(email: String, password: String) =
        authRepo.signInWithEmail(email, password)

    fun signOut() = authRepo.signOut()

    suspend fun isCurrentUserAdmin(): Boolean = authRepo.isCurrentUserAdmin()
}
