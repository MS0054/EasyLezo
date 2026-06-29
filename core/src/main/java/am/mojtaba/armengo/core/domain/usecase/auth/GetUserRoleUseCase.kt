package am.mojtaba.armengo.core.domain.usecase.auth

import am.mojtaba.armengo.core.data.datastore.enums.UserRole
import am.mojtaba.armengo.core.domain.repository.AuthRepository
import javax.inject.Inject

class GetUserRoleUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): UserRole {
        return if (authRepository.isCurrentUserAdmin()) UserRole.ADMIN else UserRole.USER }
}