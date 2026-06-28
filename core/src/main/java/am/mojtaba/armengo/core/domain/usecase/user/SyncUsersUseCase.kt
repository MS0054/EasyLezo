package am.mojtaba.armengo.core.domain.usecase.user

import am.mojtaba.armengo.core.domain.repository.UserRepository
import javax.inject.Inject

class SyncUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(limit: Long): Result<Unit> {
        return userRepository.syncUsers(limit)
    }
}