package com.angelika.kitsu.di

import com.angelika.kitsu.data.remote.RetrofitClient
import com.angelika.kitsu.data.remote.apiservice.KitsuApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    val retrofitClient: RetrofitClient = RetrofitClient()

    @Singleton
    @Provides
    fun provideMangaApiService(): KitsuApiService {
        return retrofitClient.mangaApi
    }
}