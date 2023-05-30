package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.entity.WebEntity
import kotlinx.coroutines.flow.Flow

interface WebRepository {
    fun getWebPagingSource(
        query: String,
        sort: String,
        limit: Int
    ) : Flow<PagingData<WebEntity>>
}