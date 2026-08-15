package am.mojtaba.armengo.core.domain.usecase.auth

import am.mojtaba.armengo.core.domain.repository.AuthRepository
import am.mojtaba.armengo.core.domain.repository.UserRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() {
        authRepository.signOut()
        userRepository.syncUser(null)
    }
}