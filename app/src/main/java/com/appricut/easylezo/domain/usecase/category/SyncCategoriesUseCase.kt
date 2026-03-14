package com.appricut.easylezo.domain.usecase.category

import com.appricut.easylezo.domain.repository.CategoryRepository
import javax.inject.Inject

class SyncCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke() {
        categoryRepository.syncCategories()
    }
}