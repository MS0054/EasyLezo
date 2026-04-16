package com.appricut.easylezo.core.domain.usecase.category

import com.appricut.easylezo.core.domain.model.Category
import com.appricut.easylezo.core.domain.repository.CategoryRepository
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(category: Category) {
//        categoryRepository.deleteCategoryLocal(category)
        try {
            categoryRepository.deleteCategoryServer(category)
        } catch (e: Exception) {
            // Handle error or let WorkManager handle retry later
            e.printStackTrace()
        }
    }
}