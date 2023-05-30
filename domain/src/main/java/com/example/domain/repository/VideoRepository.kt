package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.entity.VideoEntity
import kotlinx.coroutines.flow.Flow


interface VideoRepository {
    fun getVideoPagingSource(
        query: String,
        sort: String,
        limit: Int
    ) : Flow<PagingData<VideoEntity>>
}