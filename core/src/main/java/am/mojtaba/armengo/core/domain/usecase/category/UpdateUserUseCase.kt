package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.model.User
import am.mojtaba.armengo.core.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        try {
            userRepository.updateUser(user)
        } catch (e: Exception) {
            // اگر آفلاین بود، اینجا مدیریت می‌شود (مثلاً با WorkManager برای بعدا)
        }
    }

}