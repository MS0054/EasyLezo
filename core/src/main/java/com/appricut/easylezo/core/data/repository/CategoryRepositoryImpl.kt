package com.appricut.easylezo.core.data.repository

import com.appricut.easylezo.core.data.local.dao.CategoryDao
import com.appricut.easylezo.core.data.mapper.toDomain
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
        return categoryDao.observe()
            .map { list -> list?.map { it?.toDomain() ?: Category() } ?: emptyList() }
    }
    override fun observeUnsyncedStatus(): Flow<Boolean> {
        return categoryDao.observeUnsyncedStatus()
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
        return categoryDao.upsert(category.toEntity().copy(isSynced = false))
    }
    override suspend fun updateCategoryLocal(category: Category) {
        return categoryDao.upsert(category.toEntity().copy(isSynced = false))
    }
    override suspend fun deleteCategoryLocal(id: String) {
        return categoryDao.softDelete(id)
    }
    override suspend fun sortCategoryLocal(categories: List<Category>) {
        return categoryDao.upsertAll(categories.map { it.toEntity().copy(isSynced = false) })
    }
}
