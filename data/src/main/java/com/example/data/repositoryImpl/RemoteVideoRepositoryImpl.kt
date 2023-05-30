package com.example.data.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.data.remote.api.VideoApi
import com.example.data.remote.mapper.VideoMapper
import com.example.data.remote.paging.VideoPagingSource
import com.example.domain.repository.VideoRepository
import javax.inject.Inject

class RemoteVideoRepositoryImpl @Inject constructor(
    private val videoApi: VideoApi,
    private val videoMapper: VideoMapper,
) : VideoRepository{
    override fun getVideoPagingSource(
        query: String,
        sort: String,
        limit: Int,
    ) = Pager(
        config = PagingConfig(limit)
    ){
        VideoPagingSource(videoApi, videoMapper, query, sort, limit)
    }.flow
}