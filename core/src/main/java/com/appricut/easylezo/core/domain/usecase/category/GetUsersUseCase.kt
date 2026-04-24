package com.appricut.easylezo.core.domain.usecase.category

import com.appricut.easylezo.core.domain.model.User
import com.appricut.easylezo.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<List<User>> {
        return userRepository.observeUsers()
    }

}