package am.mojtaba.armengo.core.data.repository

import am.mojtaba.armengo.core.data.local.dao.CategoryDao
import am.mojtaba.armengo.core.data.mapper.toDomain
import am.mojtaba.armengo.core.data.mapper.toEntity
import am.mojtaba.armengo.core.data.remote.api.CategoryApi
import am.mojtaba.armengo.core.domain.model.Category
import am.mojtaba.armengo.core.domain.repository.CategoryRepository
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
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

    override fun observe(): Flow<List<Category>> = categoryDao.observe().map { list -> list?.map { it?.toDomain() ?: Category() } ?: emptyList() }
    override fun observeUnsyncedStatus(): Flow<Boolean> = categoryDao.observeUnsyncedStatus()

    override suspend fun syncFromServer(isForce: Boolean): Result<Unit> {
        return try {
            val metadata = metadataRepository.observeMetadata().first()
            if (metadata.lastUpdate.existNewCategoryData || isForce) {
                val newCategories = categoryApi.getCategories()
//
                categoryDao.upsertAll(newCategories.map { it.toEntity() })
                categoryDao.deleteOldIds(newCategories.map { it.id })

                val updatedMetadata = metadata.copy(lastUpdate = metadata.lastUpdate.copy(existNewCategoryData = false))
                metadataRepository.clearAndInsert(updatedMetadata)
            }
            Result.success(Unit)
        } catch (e: Exception) {
                Result.failure(e)
        }
    }
    override suspend fun addCategoryLocal(category: Category) = categoryDao.upsert(category.toEntity().copy(isSynced = false))
    override suspend fun updateCategoryLocal(category: Category) = categoryDao.upsert(category.toEntity().copy(isSynced = false))
    override suspend fun deleteCategoryLocal(id: String) = categoryDao.softDelete(id)
    override suspend fun sortCategoryLocal(categories: List<Category>) =  categoryDao.upsertAll(categories.map { it.toEntity().copy(isSynced = false) })

}
