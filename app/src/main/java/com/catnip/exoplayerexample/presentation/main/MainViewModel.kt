package com.catnip.exoplayerexample.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    fun setPlayedVideo(videoItem: VideoItem){
        playedVideo.postValue(videoItem)
    }
}