package am.mojtaba.armengo.core.domain.usecase.auth

import am.mojtaba.armengo.core.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repository: AuthRepository)
{
    suspend operator fun invoke() = repository.signOut()
}