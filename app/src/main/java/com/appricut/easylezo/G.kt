package com.appricut.easylezo

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class G : Application() {


    override fun onCreate() {
        super.onCreate()
        instance = this
//        Hawk.init(this).build()

        FirebaseApp.initializeApp(this)

//        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
//        val configSettings = remoteConfigSettings {
//            minimumFetchIntervalInSeconds = 3600
//        }
//        remoteConfig.setConfigSettingsAsync(configSettings)

    }

    companion object {
        lateinit var instance: G
            private set


    }
}