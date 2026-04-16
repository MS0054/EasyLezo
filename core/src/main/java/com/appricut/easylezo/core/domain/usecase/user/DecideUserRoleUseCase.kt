package com.appricut.easylezo.core.domain.usecase.user

import com.appricut.easylezo.core.data.datastore.enums.UserRole
import com.appricut.easylezo.core.domain.repository.AuthRepository
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