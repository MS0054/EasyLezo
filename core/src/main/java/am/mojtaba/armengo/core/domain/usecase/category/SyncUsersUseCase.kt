package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.repository.UserRepository
import javax.inject.Inject

class SyncUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(limit: Long) {
        userRepository.syncUsers(limit)
    }
}