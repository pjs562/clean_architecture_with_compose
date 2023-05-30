package com.example.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.remote.api.VideoApi
import com.example.data.remote.mapper.VideoMapper
import com.example.domain.entity.VideoEntity

class VideoPagingSource(
    private val videoApi: VideoApi,
    private val videoMapper: VideoMapper,
    private val query: String,
    private val sort: String,
    private val limit: Int
) : PagingSource<Int, VideoEntity>(){
    override fun getRefreshKey(state: PagingState<Int, VideoEntity>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoEntity> =
        try {
            val nextPage = params.key ?: 1
            val response = videoApi.getVideoList(query, sort, nextPage, limit)
            val data = response.let {
                videoMapper.fromResponse(it)
            }

            LoadResult.Page(
                data = data,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (response.meta.is_end) null else nextPage + 1
            )
        } catch (e: java.lang.Exception){
            LoadResult.Error(e)
        }
}