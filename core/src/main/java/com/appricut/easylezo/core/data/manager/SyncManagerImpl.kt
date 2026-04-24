package com.appricut.easylezo.core.data.manager

import android.content.Context
import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.appricut.easylezo.core.domain.manager.SyncManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SyncManagerImpl (
    private val context: Context
) : SyncManager {
    val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

    override fun syncCategory(workerTag: String) {
        val syncRequest = OneTimeWorkRequestBuilder<CategoryWorker>().setConstraints(constraints).addTag(workerTag).setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 1, TimeUnit.MINUTES).build()
        WorkManager.getInstance(context).enqueueUniqueWork("category_sync_work", ExistingWorkPolicy.REPLACE, syncRequest)
    }
}
