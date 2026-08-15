package am.mojtaba.armengo.core.domain.usecase.auth

import am.mojtaba.armengo.core.domain.repository.AuthRepository
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): Result<String> {
        return authRepository.signInWithGoogle(idToken)
    }
}