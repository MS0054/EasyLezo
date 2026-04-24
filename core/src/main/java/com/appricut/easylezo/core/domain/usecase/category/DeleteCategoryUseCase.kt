package com.appricut.easylezo.core.domain.usecase.category

import com.appricut.easylezo.core.domain.repository.CategoryRepository
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(id: String) {
        categoryRepository.deleteCategoryLocal(id)
    }
}