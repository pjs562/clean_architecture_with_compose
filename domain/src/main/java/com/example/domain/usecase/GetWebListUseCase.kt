package com.example.domain.usecase

import com.example.domain.repository.WebRepository
import javax.inject.Inject

class GetWebListUseCase @Inject constructor(
    private val webRepository: WebRepository,
){
    operator fun invoke(
        query: String,
        sort: String = "accuracy",
        limit: Int = 20
    ) = webRepository.getWebPagingSource(query, sort, limit)
}