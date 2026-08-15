package am.mojtaba.armengo.core.util

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val exoPlayer: ExoPlayer by lazy {
        ExoPlayer.Builder(context).build()
    }

    fun playAudio(url: String) {
        try {
            exoPlayer.stop()
            exoPlayer.clearMediaItems()

            val mediaItem = MediaItem.fromUri(url)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stopAudio() {
        exoPlayer.stop()
    }

    fun release() {
        exoPlayer.release()
    }
}