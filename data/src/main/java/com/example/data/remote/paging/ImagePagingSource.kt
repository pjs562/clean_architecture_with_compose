package com.example.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.remote.api.ImageApi
import com.example.data.remote.mapper.ImageMapper
import com.example.domain.entity.ImageEntity

class ImagePagingSource(
    private val imageApi: ImageApi,
    private val imageMapper: ImageMapper,
    private val query: String,
    private val sort: String,
    private val limit: Int
) : PagingSource<Int, ImageEntity>(){
    override fun getRefreshKey(state: PagingState<Int, ImageEntity>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageEntity> =
        try {
            val nextPage = params.key ?: 1
            val response = imageApi.getImageList(query, sort, nextPage, limit)
            val data = response.let {
                imageMapper.fromResponse(it)
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