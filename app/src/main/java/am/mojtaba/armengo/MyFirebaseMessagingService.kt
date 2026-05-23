package am.mojtaba.armengo

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification?.title
            ?: remoteMessage.data["title"]

        val body = remoteMessage.notification?.body
            ?: remoteMessage.data["body"]

        Log.i("MOJI", "onMessageReceived: $title $body")
    }

    override fun onNewToken(token: String) {
        // send to server
    }
}