package com.appricut.easylezo.core.domain.usecase.category

import com.appricut.easylezo.core.domain.model.User
import com.appricut.easylezo.core.domain.repository.UserRepository
import javax.inject.Inject

class SearchUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(name: String?, email: String?): List<User> {
        return userRepository.searchUser( name, email)
    }
}