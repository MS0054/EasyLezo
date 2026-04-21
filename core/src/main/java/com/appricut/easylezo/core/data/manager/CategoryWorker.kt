package com.appricut.easylezo.core.data.manager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.appricut.easylezo.core.data.local.dao.CategoryDao
import com.appricut.easylezo.core.data.mapper.toDto
import com.appricut.easylezo.core.data.remote.api.CategoryApi
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class CategoryWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val categoryDao: CategoryDao,
    private val categoryApi: CategoryApi
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val items = categoryDao.observeUnsynced()
            if (items.isNotEmpty()) {
                categoryApi.syncCategories(items.map { it.toDto() })
                categoryDao.markAsSynced(items.map { it.id })
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}