package com.appricut.easylezo.core.data.repository

import com.appricut.easylezo.core.data.local.dao.CategoryDao
import com.appricut.easylezo.core.data.mapper.toDomain
import com.appricut.easylezo.core.data.mapper.toDto
import com.appricut.easylezo.core.data.mapper.toEntity
import com.appricut.easylezo.core.data.remote.api.CategoryApi
import com.appricut.easylezo.core.domain.model.Category
import com.appricut.easylezo.core.domain.repository.CategoryRepository
import com.appricut.easylezo.core.domain.repository.MetadataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.map

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val metadataRepository: MetadataRepository,
    private val categoryDao: CategoryDao,
    private val categoryApi: CategoryApi

) : CategoryRepository {

    override fun observe(): Flow<List<Category>> {
        return categoryDao.observeCategories()
            .map { list -> list?.map { it?.toDomain() ?: Category() } ?: emptyList() }
    }

    override suspend fun syncLocal(isForce: Boolean) {

        val metadata = metadataRepository.observeMetadata().first()
        if (metadata.lastUpdate.existNewCategoryData || isForce) {
            val newCategories = categoryApi.getCategories()
//
            categoryDao.upsertAll(newCategories.map { it.toEntity() })
            categoryDao.deleteOldIds(newCategories.map { it.id })

            metadata.lastUpdate.existNewCategoryData = false
            metadataRepository.clearAndInsert(metadata)
        }
    }

    override suspend fun addCategoryLocal(category: Category) {
        return categoryDao.upsert(category.toEntity())
    }
    override suspend fun addCategoryServer(category: Category) {
        categoryApi.addCategory(category.toDto())
        addCategoryLocal(category)
    }
    override suspend fun updateCategoryLocal(category: Category) {
        return categoryDao.upsert(category.toEntity())
    }
    override suspend fun updateCategoryServer(category: Category) {
        categoryApi.updateCategory(category.toDto())
        updateCategoryLocal(category)
    }
    override suspend fun deleteCategoryLocal(category: Category) {
        return categoryDao.delete(category.toEntity())
    }
    override suspend fun deleteCategoryServer(category: Category) {
        categoryApi.deleteCategory(category.toDto())
        deleteCategoryLocal(category)
    }
    override suspend fun sortCategoryLocal(categories: List<Category>) {
        return categoryDao.upsertAll(categories.map { it.toEntity() })
    }
    override suspend fun sortCategoryServer(categories: List<Category>) {
        categoryApi.sortCategories(categories.map { it.toDto() })
        sortCategoryLocal(categories)
    }
}
