package com.appricut.easylezo.domain.usecase.category

import com.appricut.easylezo.domain.model.Category
import com.appricut.easylezo.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(): Flow<List<Category>> {
        return categoryRepository.observeCategories()
    }

}