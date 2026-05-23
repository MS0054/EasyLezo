package am.mojtaba.armengo.core.domain.repository

import am.mojtaba.armengo.core.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun observe(): Flow<List<Category>>
    fun observeUnsyncedStatus(): Flow<Boolean>
    suspend fun syncLocal(isForce: Boolean)
    suspend fun addCategoryLocal(category: Category)
    suspend fun updateCategoryLocal(category: Category)
    suspend fun deleteCategoryLocal(id: String)
    suspend fun sortCategoryLocal(categories: List<Category>)

}