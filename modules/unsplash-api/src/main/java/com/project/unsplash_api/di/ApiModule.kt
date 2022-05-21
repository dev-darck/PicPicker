package com.project.unsplash_api.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.project.unsplash_api.BuildConfig
import com.project.unsplash_api.api.UnsplashApi
import com.project.unsplash_api.api.UnsplashRepository
import com.project.unsplash_api.api.UnsplashRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DateFormat
import javax.inject.Singleton

private const val BASE_URL = BuildConfig.BASE_URL

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .serializeNulls()
        .setDateFormat(DateFormat.LONG)
        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
        .setPrettyPrinting()
        .create()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    @Provides
    @Singleton
    fun provideApiClient(retrofit: Retrofit): UnsplashApi = retrofit.create(UnsplashApi::class.java)

    @Provides
    @Singleton
    fun provideUnsplashRepository(unsplashApi: UnsplashApi): UnsplashRepository =
        UnsplashRepositoryImpl(unsplashApi)
}