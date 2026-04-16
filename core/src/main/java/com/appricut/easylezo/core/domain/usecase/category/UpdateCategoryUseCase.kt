package com.appricut.easylezo.core.domain.usecase.category

import com.appricut.easylezo.core.domain.model.Category
import com.appricut.easylezo.core.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(category: Category) {
//         categoryRepository.updateCategoryLocal(category)
        try {
            categoryRepository.updateCategoryServer(category)
        } catch (e: Exception) {
            // اگر آفلاین بود، اینجا مدیریت می‌شود (مثلاً با WorkManager برای بعدا)
        }
    }

}