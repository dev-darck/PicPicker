package com.project.unsplash_api.di

import android.content.Context
import com.project.unsplash_api.BuildConfig
import com.project.unsplash_api.extensions.safeRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.net.ConnectException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val CACHE_END_PATH = "http-cache"
private const val CACHE_SIZE = 5 * 1024 * 1024L

@Module
@InstallIn(SingletonComponent::class)
object SettingsApi {

    @Provides
    @Singleton
    fun provideClient(
        interceptors: Set<@JvmSuppressWildcards Interceptor>,
        @Network networkInterceptor: Set<@JvmSuppressWildcards Interceptor>,
        cache: Cache,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.cache(cache)
        networkInterceptor.forEach {
            builder.addNetworkInterceptor(it)
        }
        interceptors.forEach {
            builder.addInterceptor(it)
        }
        return builder
            .retryOnConnectionFailure(false)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    @IntoSet
    @Provides
    @Singleton
    fun provideOfflineInterceptor(): Interceptor {
        return Interceptor { chain ->
            try {
                chain.safeRequest(chain.request())
            } catch (e: ConnectException) {
                val cacheControl = CacheControl.Builder()
                    .onlyIfCached()
                    .maxStale(1, TimeUnit.DAYS)
                    .build()

                val offlineRequest = chain.request().newBuilder()
                    .removeHeader("Pragma")
                    .cacheControl(cacheControl)
                    .build()
                chain.safeRequest(offlineRequest)
            }
        }
    }

    @Network
    @IntoSet
    @Provides
    @Singleton
    fun provideNetworkInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val cacheHeader = original.header("CACHE_HEADER")
            val builder = original.newBuilder()
            val accessKey = BuildConfig.UNSPLASH_ACCESS_KEY
            //Добавить проверку на Id user's
            builder.addHeader("Authorization", "Client-ID $accessKey")

            if (cacheHeader == null || cacheHeader.contains("no-cache")
                || cacheHeader.contains("max-stale=0")
            ) {

                val cacheControl = CacheControl.Builder()
                    .maxAge(15, TimeUnit.MINUTES)
                    .build()

                builder
                    .removeHeader("Pragma")
                    .cacheControl(cacheControl)
                chain.safeRequest(builder.build())
            } else {
                chain.safeRequest(builder.build())
            }
        }
    }

    @IntoSet
    @Provides
    @Singleton
    fun provideLoggerInterceptor(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        loggingInterceptor.redactHeader("Authorization")
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache {
        val cacheFile = File(context.cacheDir, CACHE_END_PATH)
        return Cache(cacheFile, CACHE_SIZE)
    }
}