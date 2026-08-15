package am.mojtaba.armengo.core.domain.usecase.user

import am.mojtaba.armengo.core.domain.model.User
import am.mojtaba.armengo.core.domain.repository.AuthRepository
import am.mojtaba.armengo.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<User?> {
        return userRepository.observeUser().map { user ->
            val currentUid = authRepository.getCurrentUserUid()
            if (currentUid != null && user.uid == currentUid) {
                user
            } else {
                null
            }
        }
    }
}
