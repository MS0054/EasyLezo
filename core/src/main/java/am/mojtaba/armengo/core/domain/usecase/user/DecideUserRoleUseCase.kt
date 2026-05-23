package am.mojtaba.armengo.core.domain.usecase.user

import am.mojtaba.armengo.core.data.datastore.enums.UserRole
import am.mojtaba.armengo.core.domain.repository.AuthRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DecideUserRoleUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): UserRole {
        authRepository.checkAdmin()
        return if (
            authRepository.isAdmin().filterNotNull().first()) UserRole.ADMIN else UserRole.USER
    }
}