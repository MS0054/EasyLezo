package com.appricut.easylezo.core.domain.usecase.category

import com.appricut.easylezo.core.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveUnsyncedStatusUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return categoryRepository.observeUnsyncedStatus()
    }
}