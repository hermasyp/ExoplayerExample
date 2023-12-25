package com.catnip.exoplayerexample.presentation.main

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.media3.ui.PlayerView
import com.catnip.exoplayerexample.data.datasource.VideosDataSource
import com.catnip.exoplayerexample.data.model.VideoItem
import com.catnip.exoplayerexample.presentation.player.ExoPlayerManager

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class MainViewModel(
    private val dataSource: VideosDataSource,
    private val playerManager: ExoPlayerManager
) : ViewModel() {

    val playedVideo = MutableLiveData<VideoItem>()

    fun getVideos() = dataSource.getVideos()

    fun bindPlayer(lifecycle: Lifecycle) {
        lifecycle.addObserver(playerManager)
    }
    fun releasePlayer(lifecycle: Lifecycle) {
        lifecycle.removeObserver(playerManager)
    }
    fun setPlayedVideo(playerView: PlayerView, videoItem: VideoItem) {
        playedVideo.postValue(videoItem)
        playerManager.play(playerView, videoItem)
    }
}