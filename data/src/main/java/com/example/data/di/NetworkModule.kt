package com.example.data.di

import com.example.data.remote.api.ImageApi
import com.example.data.remote.api.VideoApi
import com.example.data.remote.api.WebApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    @Provides
    @Singleton
    fun provideAuthInterceptor() = Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "KakaoAK 6554879cf60ce4b225215a912d151e2d")
            .build()
        chain.proceed(newRequest)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: Interceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://dapi.kakao.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideWebApi(
        retrofit: Retrofit
    ): WebApi = retrofit.create(WebApi::class.java)

    @Provides
    @Singleton
    fun provideVideoApi(
        retrofit: Retrofit
    ): VideoApi = retrofit.create(VideoApi::class.java)

    @Provides
    @Singleton
    fun provideImageApi(
        retrofit: Retrofit
    ): ImageApi = retrofit.create(ImageApi::class.java)

}