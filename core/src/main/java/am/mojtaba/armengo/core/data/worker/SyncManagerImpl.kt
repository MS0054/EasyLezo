package am.mojtaba.armengo.core.data.worker

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import am.mojtaba.armengo.core.domain.manager.SyncManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncManagerImpl @Inject constructor(
    private val context: Context
) : SyncManager {

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    override fun syncCategoryToServer(workerTag: String) {
        val syncRequest = OneTimeWorkRequestBuilder<CategoryWorker>()
            .setConstraints(constraints)
            .addTag(workerTag)
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 1, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "category_sync_work",
            ExistingWorkPolicy.REPLACE,
            syncRequest
        )
    }

    override fun syncLanguageToServer(workerTag: String) {
        val syncRequest = OneTimeWorkRequestBuilder<LanguageWorker>()
            .setConstraints(constraints)
            .addTag(workerTag)
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 1, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "language_sync_work",
            ExistingWorkPolicy.REPLACE,
            syncRequest
        )
    }

    override fun syncSentenceToServer(workerTag: String) {
        val syncRequest = OneTimeWorkRequestBuilder<SentenceWorker>()
            .setConstraints(constraints)
            .addTag(workerTag)
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 1, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "sentence_sync_work",
            ExistingWorkPolicy.REPLACE,
            syncRequest
        )
    }

    override fun syncWordToServer(workerTag: String) {
        val syncRequest = OneTimeWorkRequestBuilder<WordWorker>()
            .setConstraints(constraints)
            .addTag(workerTag)
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 1, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "word_sync_work",
            ExistingWorkPolicy.REPLACE,
            syncRequest
        )
    }
}