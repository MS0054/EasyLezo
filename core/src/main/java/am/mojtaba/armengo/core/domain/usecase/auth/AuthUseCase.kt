package am.mojtaba.armengo.core.domain.usecase.auth

import am.mojtaba.armengo.core.data.repository.AuthRepository2
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