package am.mojtaba.armengo.core.data.worker

import am.mojtaba.armengo.core.data.local.dao.CategoryWordDao
import am.mojtaba.armengo.core.data.mapper.toDto
import am.mojtaba.armengo.core.data.remote.api.CategoryWordApi
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class CategoryWordWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val categoryWordDao: CategoryWordDao,
    private val categoryWordApi: CategoryWordApi
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val items = categoryWordDao.observeUnsynced()
            if (items.isNotEmpty()) {
                categoryWordApi.syncCategoryWords(items.map { it.toDto() })
                categoryWordDao.markAsSynced(items.map { it.categoryId })
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
