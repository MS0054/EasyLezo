package com.appricut.easylezo.core.domain.usecase.category

import com.appricut.easylezo.core.domain.repository.CategoryRepository
import javax.inject.Inject

class SyncCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(isForce: Boolean = false) {
        categoryRepository.syncLocal(isForce)
    }
}