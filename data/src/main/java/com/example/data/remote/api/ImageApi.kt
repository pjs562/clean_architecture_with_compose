package com.example.data.remote.api

import com.example.data.remote.response.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {
    @GET("/v2/search/image")
    suspend fun getImageList(
        @Query("query") query: String,
        @Query("sort") sort: String = "accuracy",
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): ImageResponse
}