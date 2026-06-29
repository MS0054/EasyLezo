package am.mojtaba.armengo.core.data.manager

import am.mojtaba.armengo.core.data.local.dao.SentenceDao
import am.mojtaba.armengo.core.data.mapper.toDto
import am.mojtaba.armengo.core.data.remote.api.SentenceApi
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SentenceWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val sentenceDao: SentenceDao,
    private val sentenceApi: SentenceApi
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val items = sentenceDao.observeUnsynced()
            if (items.isNotEmpty()) {
                sentenceApi.syncSentences(items.map { it.toDto() })
                sentenceDao.markAsSynced(items.map { it.id })
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}