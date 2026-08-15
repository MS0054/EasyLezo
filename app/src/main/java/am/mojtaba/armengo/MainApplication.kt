package am.mojtaba.armengo

import android.app.Application
import android.util.Log
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this

        MobileAds.initialize(this) {}

        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            Log.d("FCM_TOKEN", token)
        }

        FirebaseApp.initializeApp(this)

//        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
//        val configSettings = remoteConfigSettings {
//            minimumFetchIntervalInSeconds = 3600
//        }
//        remoteConfig.setConfigSettingsAsync(configSettings)

    }

    companion object {
        lateinit var instance: MainApplication
            private set
    }
}