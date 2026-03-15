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

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val metadataRepository: MetadataRepository,
    private val categoryDao: CategoryDao,
    private val categoryApi: CategoryApi

) : CategoryRepository {


    override fun observeCategories(): Flow<List<Category>> {
        return categoryDao.observeCategories()
            .map { list -> list?.map { it?.toDomain() ?: Category() } ?: emptyList() }
    }


    override suspend fun syncCategories() {
        val metadata = metadataRepository.observeMetadata().first()
        if (metadata.lastUpdate.existNewCategoryData) {
            val newCategories = categoryApi.getCategories()
            categoryDao.clearAll()
            categoryDao.insertAll(newCategories.map { it.toEntity() })

            metadata.lastUpdate.existNewCategoryData = false
            metadataRepository.clearAndInsert(metadata)
        }
    }
}
