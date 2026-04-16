package com.appricut.easylezo.core.domain.usecase.category

import com.appricut.easylezo.core.domain.model.Category
import com.appricut.easylezo.core.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SortCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(categories: List<Category>) {
//        categoryRepository.sortCategoryLocal(categories)
        try {
            categoryRepository.sortCategoryServer(categories)
        } catch (e: Exception) {
        }
    }

}