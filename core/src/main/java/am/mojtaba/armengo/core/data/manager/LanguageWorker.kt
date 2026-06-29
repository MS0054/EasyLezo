package am.mojtaba.armengo.core.data.manager

import am.mojtaba.armengo.core.data.local.dao.LanguageDao
import am.mojtaba.armengo.core.data.mapper.toDto
import am.mojtaba.armengo.core.data.remote.api.LanguageApi
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class LanguageWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val languageDao: LanguageDao,
    private val languageApi: LanguageApi
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val items = languageDao.observeUnsynced()
            if (items.isNotEmpty()) {
                languageApi.syncLanguages(items.map { it.toDto() })
                languageDao.markAsSynced(items.map { it.id })
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}