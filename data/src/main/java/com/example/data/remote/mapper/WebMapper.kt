package com.example.data.remote.mapper

import com.example.data.extensions.decode
import com.example.data.remote.response.WebInfo
import com.example.data.remote.response.WebResponse
import com.example.domain.entity.WebEntity

class WebMapper {
    fun fromResponse(
        response: WebResponse
    ): List<WebEntity> = response.documents.map {
        fromReponse(it)
    }

    private fun fromReponse(
        response: WebInfo
    ) : WebEntity = WebEntity(
        response.title.decode(),
        response.contents.decode(),
        response.url,
        response.datetime
    )
}