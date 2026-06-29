package am.mojtaba.armengo.core.domain.usecase.auth

import am.mojtaba.armengo.core.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthRepository)
{
    suspend operator fun invoke(email: String, password: String): Result<String> {
        return repository.signIn(email, password)
    }
}