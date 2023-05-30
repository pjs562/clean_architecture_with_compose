package com.example.data.remote.response

import com.squareup.moshi.Json

data class ImageResponse(
    @field:Json(name = "meta")
    val meta: MetaData,
    @field:Json(name = "documents")
    val documents: List<ImageInfo>,
)

data class ImageInfo(
    @field:Json(name = "collection")
    val collection: String,
    @field:Json(name = "thumbnail_url")
    val thumbnailUrl: String,
    @field:Json(name = "image_url")
    val imageUrl: String,
    @field:Json(name = "width")
    val width: String,
    @field:Json(name = "height")
    val height: String,
    @field:Json(name = "display_sitename")
    val displaySitename: String,
    @field:Json(name = "doc_url")
    val docUrl: String,
    @field:Json(name = "datetime")
    val datetime: String,
)
