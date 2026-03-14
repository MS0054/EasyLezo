package com.appricut.easylezo.domain.usecase.user

import com.appricut.easylezo.domain.repository.AuthRepository
import com.appricut.easylezo.domain.repository.UserRepository
import javax.inject.Inject

class SyncUserUseCase @Inject constructor(
    private val repository: UserRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        repository.syncUser(authRepository.getUid())
    }
}