package com.example.data.remote.response

import com.squareup.moshi.Json


data class WebResponse(
    @field:Json(name = "meta")
    val meta: MetaData,
    @field:Json(name = "documents")
    val documents: List<WebInfo>,
)

data class WebInfo(
    @field:Json(name = "datetime")
    val datetime: String,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "contents")
    val contents: String,
    @field:Json(name = "url")
    val url: String
)
