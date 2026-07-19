package am.mojtaba.armengo.core.data.worker

import am.mojtaba.armengo.core.data.local.dao.WordDao
import am.mojtaba.armengo.core.data.mapper.toDto
import am.mojtaba.armengo.core.data.remote.api.WordApi
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class WordWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val wordDao: WordDao,
    private val wordApi: WordApi
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val items = wordDao.observeUnsynced()
            if (items.isNotEmpty()) {
                wordApi.syncWords(items.map { it.toDto() })
                wordDao.markAsSynced(items.map { it.id })
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}