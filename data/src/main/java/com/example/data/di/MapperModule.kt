package com.example.data.di

import com.example.data.remote.mapper.ImageMapper
import com.example.data.remote.mapper.VideoMapper
import com.example.data.remote.mapper.WebMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {
    @Provides
    @Singleton
    fun provideWebMapper(): WebMapper = WebMapper()

    @Provides
    @Singleton
    fun provideVideoMapper(): VideoMapper = VideoMapper()

    @Provides
    @Singleton
    fun provideImageMapper(): ImageMapper = ImageMapper()
}