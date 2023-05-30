package com.example.domain.di

import com.example.domain.repository.ImageRepository
import com.example.domain.repository.VideoRepository
import com.example.domain.repository.WebRepository
import com.example.domain.usecase.GetImageListUseCase
import com.example.domain.usecase.GetVideoListUseCase
import com.example.domain.usecase.GetWebListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideGetWebListUseCase(
        webRepository: WebRepository
    ): GetWebListUseCase = GetWebListUseCase(webRepository)

    @Provides
    @Singleton
    fun provideGetVideoListUseCase(
        videoRepository: VideoRepository
    ): GetVideoListUseCase = GetVideoListUseCase(videoRepository)

    @Provides
    @Singleton
    fun provideGetImageListUseCase(
        imageRepository: ImageRepository
    ): GetImageListUseCase = GetImageListUseCase(imageRepository)
}