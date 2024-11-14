package com.challenge.gamesearch.di

import com.challenge.gamesearch.data.IGameApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModules {
    @Provides
    @Singleton
    // TODO: add retries intervals
    fun provideHttpClient() : OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    // TODO: Add url to a secure configuration file
    fun provideRetrofit(httpClient: OkHttpClient) : Retrofit =
        Retrofit
            .Builder()
            .baseUrl("https://www.freetogame.com/api/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit) : IGameApiService = retrofit.create(IGameApiService::class.java)
}