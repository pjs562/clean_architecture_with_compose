package com.example.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.remote.api.WebApi
import com.example.data.remote.mapper.WebMapper
import com.example.domain.entity.WebEntity

class WebPagingSource(
    private val webApi: WebApi,
    private val webMapper: WebMapper,
    private val query: String,
    private val sort: String,
    private val limit: Int
) : PagingSource<Int, WebEntity>(){
    override fun getRefreshKey(state: PagingState<Int, WebEntity>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WebEntity> =
        try {
            val nextPage = params.key ?: 1
            val response = webApi.getWebList(query, sort, nextPage, limit)
            val data = response.let {
                webMapper.fromResponse(it)
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