package com.wing.tree.just.weather.service

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.wing.tree.just.weather.service.data.worker.UpdateWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var hiltWorkerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .build()

        val builder = PeriodicWorkRequest.Builder(
            UpdateWorker::class.java,
            REPEAT_INTERVAL,
            TimeUnit.HOURS
        ).setConstraints(constraints)

        val periodicWorkRequest = builder.build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                UpdateWorker.UNIQUE_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWorkRequest
            )
    }

    override fun getWorkManagerConfiguration() = Configuration.Builder()
        .setWorkerFactory(hiltWorkerFactory)
        .build()

    companion object {
        private const val REPEAT_INTERVAL = 3L
    }
}