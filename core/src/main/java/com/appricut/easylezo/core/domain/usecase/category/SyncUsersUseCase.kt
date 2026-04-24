package com.appricut.easylezo.core.domain.usecase.category

import com.appricut.easylezo.core.domain.repository.UserRepository
import com.google.api.QuotaLimit
import javax.inject.Inject

class SyncUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(limit: Long) {
        userRepository.syncUsers(limit)
    }
}