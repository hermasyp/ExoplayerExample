package com.catnip.exoplayerexample.presentation.player

import androidx.annotation.OptIn
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.ui.PlayerView
import com.catnip.exoplayerexample.data.model.VideoItem
import org.koin.java.KoinJavaComponent.get

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class ExoPlayerManager : DefaultLifecycleObserver {

    private var player: ExoPlayer? = null
    private var msf: MediaSource.Factory? = null

    private fun initPlayer(playerView: PlayerView, fullScreenListener: (Boolean) -> Unit) {
        player = get(ExoPlayer::class.java)
        msf = get(MediaSource.Factory::class.java)
        playerView.apply {
            player = this@ExoPlayerManager.player
            useController = true
        }
        player?.apply {
            playWhenReady = true
        }
        playerView.setFullscreenButtonClickListener { isFullScreen ->
            fullScreenListener(isFullScreen)
        }
    }

    fun play(videoItem: VideoItem, playerView: PlayerView, onFullScreenListener: (Boolean) -> Unit) {
        if (player != null && msf != null) {
            releasePlayer()
        }
        initPlayer(playerView, onFullScreenListener)
        setMediaItemPlayer(videoItem)
    }

    @OptIn(UnstableApi::class)
    private fun setMediaItemPlayer(videoItem: VideoItem) {
        if (player == null && msf == null) return
        msf?.createMediaSource(buildMediaItem(videoItem))?.let { player?.setMediaSource(it) }
        player?.prepare()
        player?.playWhenReady = true
    }

    private fun buildMediaItem(videoItem: VideoItem): MediaItem {
        return MediaItem.Builder()
            .setUri(videoItem.url)
            .setMediaId(videoItem.id)
            .setTag(videoItem.title)
            .build()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        player?.pause()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        player?.play()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        releasePlayer()
    }

    private fun releasePlayer() {
        player?.release()
        player = null
        msf = null
    }
}