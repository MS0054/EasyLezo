package am.mojtaba.armengo.core.domain.usecase.user

import am.mojtaba.armengo.core.domain.repository.AuthRepository
import am.mojtaba.armengo.core.domain.repository.UserRepository
import javax.inject.Inject

class SyncUserUseCase @Inject constructor(
    private val repository: UserRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        repository.syncUser(authRepository.getUid())
    }
}