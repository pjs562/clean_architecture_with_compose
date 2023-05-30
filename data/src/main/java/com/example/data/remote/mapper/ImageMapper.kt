package com.example.data.remote.mapper

import com.example.data.remote.response.ImageInfo
import com.example.data.remote.response.ImageResponse
import com.example.domain.entity.ImageEntity

class ImageMapper {
    fun fromResponse(
        response: ImageResponse
    ): List<ImageEntity> = response.documents.map {
        fromReponse(it)
    }

    private fun fromReponse(
        response: ImageInfo
    ) : ImageEntity = ImageEntity(
        response.collection,
        response.thumbnailUrl,
        response.imageUrl,
        response.width,
        response.height,
        response.displaySitename,
        response.docUrl,
        response.datetime
    )
}