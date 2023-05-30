package com.example.data.remote.api

import com.example.data.remote.response.WebResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WebApi {
    @GET("/v2/search/web")
    suspend fun getWebList(
        @Query("query") query: String,
        @Query("sort") sort: String = "accuracy",
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): WebResponse
}