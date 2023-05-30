package com.example.data.remote.api

import com.example.data.remote.response.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoApi {
    @GET("/v2/search/vclip")
    suspend fun getVideoList(
        @Query("query") query: String,
        @Query("sort") sort: String = "accuracy",
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): VideoResponse
}