package com.example.data.di

import com.example.data.remote.api.ImageApi
import com.example.data.remote.api.VideoApi
import com.example.data.remote.api.WebApi
import com.example.data.remote.mapper.ImageMapper
import com.example.data.remote.mapper.VideoMapper
import com.example.data.remote.mapper.WebMapper
import com.example.data.repositoryImpl.RemoteImageRepositoryImpl
import com.example.data.repositoryImpl.RemoteVideoRepositoryImpl
import com.example.data.repositoryImpl.RemoteWebRepositoryImpl
import com.example.domain.repository.ImageRepository
import com.example.domain.repository.VideoRepository
import com.example.domain.repository.WebRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun remoteWebRepository(
        api: WebApi,
        mapper: WebMapper,
    ) : WebRepository = RemoteWebRepositoryImpl(api, mapper)

    @Singleton
    @Provides
    fun remoteVideoRepository(
        api: VideoApi,
        mapper: VideoMapper,
    ) : VideoRepository = RemoteVideoRepositoryImpl(api, mapper)

    @Singleton
    @Provides
    fun remoteImageRepository(
        api: ImageApi,
        mapper: ImageMapper,
    ) : ImageRepository = RemoteImageRepositoryImpl(api, mapper)
}