package com.catnip.exoplayerexample.data.datasource

import com.catnip.exoplayerexample.data.model.VideoItem

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface VideosDataSource {
    fun getVideos(): List<VideoItem>
}

class VideosDataSourceImpl() : VideosDataSource {
    override fun getVideos(): List<VideoItem> = listOf(
        VideoItem(
            title = "Video 1 Test",
            author = "Video 1",
            url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
        ),
        VideoItem(
            title = "Video 2 Test",
            author = "Video 2",
            url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
        ),
        VideoItem(
            title = "Video 3 Test",
            author = "Video 3",
            url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        ),
        VideoItem(
            title = "Video 4 Test",
            author = "Video 4",
            url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4"
        ),
        VideoItem(
            title = "Video 5 Test",
            author = "Video 5",
            url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4"
        ),
    )
}