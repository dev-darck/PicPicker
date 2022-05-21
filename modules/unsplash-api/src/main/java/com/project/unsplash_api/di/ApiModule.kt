package com.project.unsplash_api.di

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.project.unsplash_api.BuildConfig
import com.project.unsplash_api.api.UnsplashApi
import com.project.unsplash_api.api.UnsplashRepository
import com.project.unsplash_api.api.UnsplashRepositoryImpl
import com.project.unsplash_api.interceptor.NetworkInterceptor
import com.project.unsplash_api.interceptor.OfflineInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.text.DateFormat
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val CACHE_END_PATH = "http-cache"
private const val CACHE_SIZE = 5 * 1024 * 1024L
private const val BASE_URL = BuildConfig.BASE_URL

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideClient(@ApplicationContext context: Context): OkHttpClient {
        val cacheFile = File(context.cacheDir, CACHE_END_PATH)
        val cache = Cache(cacheFile, CACHE_SIZE)
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = BASIC
        loggingInterceptor.redactHeader("Authorization")
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(false)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(NetworkInterceptor)
            .addInterceptor(OfflineInterceptor)
            .addInterceptor(loggingInterceptor)
            .cache(cache)
            .build()
    }

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