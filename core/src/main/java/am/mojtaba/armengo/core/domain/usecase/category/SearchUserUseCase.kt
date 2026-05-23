package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.model.User
import am.mojtaba.armengo.core.domain.repository.UserRepository
import javax.inject.Inject

class SearchUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(name: String?, email: String?): List<User> {
        return userRepository.searchUser( name, email)
    }
}