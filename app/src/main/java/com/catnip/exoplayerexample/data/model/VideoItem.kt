package com.catnip.exoplayerexample.data.model

import java.util.UUID

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
data class VideoItem(
    val id : String = UUID.randomUUID().toString(),
    val title: String,
    val author: String,
    val url: String
)
