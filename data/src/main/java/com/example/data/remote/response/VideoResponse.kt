package com.example.data.remote.response

import com.squareup.moshi.Json

data class VideoResponse(
    @field:Json(name = "meta")
    val meta: MetaData,
    @field:Json(name = "documents")
    val documents: List<VideoInfo>,
)

data class VideoInfo(
    @field:Json(name = "datetime")
    val datetime: String,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "play_time")
    val playTime: String,
    @field:Json(name = "url")
    val url: String,
    @field:Json(name = "author")
    val author: String,
    @field:Json(name = "thumbnail")
    val thumbnail: String
)
