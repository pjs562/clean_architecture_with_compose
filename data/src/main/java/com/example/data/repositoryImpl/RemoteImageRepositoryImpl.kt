package com.example.data.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.data.remote.api.ImageApi
import com.example.data.remote.mapper.ImageMapper
import com.example.data.remote.paging.ImagePagingSource
import com.example.domain.repository.ImageRepository
import javax.inject.Inject

class RemoteImageRepositoryImpl @Inject constructor(
    private val imageApi: ImageApi,
    private val imageMapper: ImageMapper,
) : ImageRepository {
    override fun getImagePagingSource(
        query: String,
        sort: String,
        limit: Int,
    ) = Pager(
        config = PagingConfig(limit)
    ){
        ImagePagingSource(imageApi, imageMapper, query, sort, limit)
    }.flow
}