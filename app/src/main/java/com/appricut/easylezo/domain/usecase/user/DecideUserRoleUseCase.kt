package com.appricut.easylezo.domain.usecase.user

import com.appricut.easylezo.data.datastore.enums.UserRole
import com.appricut.easylezo.domain.repository.AuthRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DecideUserRoleUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): UserRole {
        authRepository.checkAdmin()
        return if (
            authRepository.isAdmin()
                .filterNotNull()
                .first()
        ) UserRole.ADMIN
        else UserRole.USER
    }
}