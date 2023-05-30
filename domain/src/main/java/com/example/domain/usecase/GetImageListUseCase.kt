package com.example.domain.usecase

import com.example.domain.repository.ImageRepository
import javax.inject.Inject

class GetImageListUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
){
    operator fun invoke(
        query: String,
        sort: String = "accuracy",
        limit: Int = 20
    ) = imageRepository.getImagePagingSource(query, sort, limit)
}