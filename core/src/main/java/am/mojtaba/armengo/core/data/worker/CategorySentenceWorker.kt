package am.mojtaba.armengo.core.data.worker

import am.mojtaba.armengo.core.data.local.dao.CategorySentenceDao
import am.mojtaba.armengo.core.data.mapper.toDto
import am.mojtaba.armengo.core.data.remote.api.CategorySentenceApi
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class CategorySentenceWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val categorySentenceDao: CategorySentenceDao,
    private val categorySentenceApi: CategorySentenceApi
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val items = categorySentenceDao.observeUnsynced()
            if (items.isNotEmpty()) {
                categorySentenceApi.syncCategorySentences(items.map { it.toDto() })
                categorySentenceDao.markAsSynced(items.map { it.categoryId })
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}