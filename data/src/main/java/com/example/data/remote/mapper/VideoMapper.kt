package com.example.data.remote.mapper

import com.example.data.remote.response.VideoInfo
import com.example.data.remote.response.VideoResponse
import com.example.domain.entity.VideoEntity

class VideoMapper {
    fun fromResponse(
        response: VideoResponse
    ): List<VideoEntity> = response.documents.map {
        fromReponse(it)
    }

    private fun fromReponse(
        response: VideoInfo
    ) : VideoEntity = VideoEntity(
        response.title,
        response.playTime,
        response.thumbnail,
        response.url,
        response.datetime,
        response.author
    )
}